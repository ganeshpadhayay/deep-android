package com.example.ganesh.deep_android.scopestorage

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.RecoverableSecurityException
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ganesh.deep_android.databinding.ActivityScopeStorageExampleBinding
import com.example.ganesh.deep_android.scopestorage.adapter.InternalStoragePhotoAdapter
import com.example.ganesh.deep_android.scopestorage.adapter.SharedPhotoAdapter
import com.example.ganesh.deep_android.scopestorage.bean.InternalStoragePhoto
import com.example.ganesh.deep_android.scopestorage.bean.SharedStoragePhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*

class ScopeStorageExampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScopeStorageExampleBinding

    private lateinit var internalStoragePhotoAdapter: InternalStoragePhotoAdapter
    private lateinit var externalStoragePhotoAdapter: SharedPhotoAdapter

    private var readPermissionGranted: Boolean = false
    private var writePermissionGranted: Boolean = false

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var intentSenderLauncher: ActivityResultLauncher<IntentSenderRequest>

    private lateinit var contentObserver: ContentObserver

    private var deletedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScopeStorageExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        internalStoragePhotoAdapter = InternalStoragePhotoAdapter {
            lifecycleScope.launch {
                val isDeletedSuccessfully = deletePhotoFromInternalStorage(it.name)
                if (isDeletedSuccessfully) {
                    loadPhotosFromInternalStorageIntoRecyclerView()
                    Toast.makeText(this@ScopeStorageExampleActivity, "Photo deleted successfully.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ScopeStorageExampleActivity, "Failed to delete photo.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        externalStoragePhotoAdapter = SharedPhotoAdapter {
            lifecycleScope.launch {
                deletePhotoFromExternalStorage(it.contentUri)
                deletedImageUri = it.contentUri
            }
        }

        setupExternalStorageRecyclerView()
        initContentObserver()

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            readPermissionGranted = permissions[READ_EXTERNAL_STORAGE] ?: readPermissionGranted
            writePermissionGranted = permissions[WRITE_EXTERNAL_STORAGE] ?: writePermissionGranted

            if (readPermissionGranted) {
                loadPhotosFromExternalStorageIntoRecyclerView()
            } else {
                Toast.makeText(this, "Can't read files without permission.", Toast.LENGTH_SHORT).show()
            }
        }

        updateOrRequestPermission()

        intentSenderLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            if (it.resultCode == RESULT_OK) {
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
                    lifecycleScope.launch {
                        deletePhotoFromExternalStorage(deletedImageUri ?: return@launch)
                    }
                }
                Toast.makeText(this@ScopeStorageExampleActivity, "Photo deleted successfully.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@ScopeStorageExampleActivity, "Failed to delete photo.", Toast.LENGTH_SHORT).show()
            }
        }

        val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            lifecycleScope.launch {
                val isPrivate = binding.switchPrivate.isChecked
                val isSavedSuccessfully = when {
                    isPrivate -> savePhotoInInternalStorage(UUID.randomUUID().toString(), it)
                    writePermissionGranted -> savePhotoToExternalStorage(UUID.randomUUID().toString(), it)
                    else -> false
                }
                if (isPrivate) {
                    loadPhotosFromInternalStorageIntoRecyclerView()
                }

                if (isSavedSuccessfully) {
                    Toast.makeText(this@ScopeStorageExampleActivity, "Photo saved successfully.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ScopeStorageExampleActivity, "Failed to save photo.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnTakePhoto.setOnClickListener {
            takePhoto.launch(null)
        }

        setupInternalStorageRecyclerView()
        loadPhotosFromInternalStorageIntoRecyclerView()
        loadPhotosFromExternalStorageIntoRecyclerView()
    }

    private fun initContentObserver() {
        contentObserver = object : ContentObserver(null) {
            override fun onChange(selfChange: Boolean) {
                if (readPermissionGranted) {
                    loadPhotosFromExternalStorageIntoRecyclerView()
                }
            }
        }
        contentResolver.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, contentObserver)
    }

    private fun updateOrRequestPermission() {
        val hasReadPermission = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        val hasWritePermission = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        val isMinSDK29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        readPermissionGranted = hasReadPermission
        writePermissionGranted = hasWritePermission || isMinSDK29

        val permissionsToRequest = mutableListOf<String>()
        if (!readPermissionGranted)
            permissionsToRequest.add(READ_EXTERNAL_STORAGE)
        if (!writePermissionGranted)
            permissionsToRequest.add(WRITE_EXTERNAL_STORAGE)

        if (permissionsToRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }

    private fun setupInternalStorageRecyclerView() = binding.rvPrivatePhotos.apply {
        adapter = internalStoragePhotoAdapter
        layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
    }

    private fun setupExternalStorageRecyclerView() = binding.rvPublicPhotos.apply {
        adapter = externalStoragePhotoAdapter
        layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
    }

    private fun loadPhotosFromInternalStorageIntoRecyclerView() {
        lifecycleScope.launch {
            val photos = loadPhotoFromInternalStorage()
            internalStoragePhotoAdapter.submitList(photos)
        }
    }

    private fun loadPhotosFromExternalStorageIntoRecyclerView() {
        lifecycleScope.launch {
            val photos = loadPhotoFromExternalStorage()
            externalStoragePhotoAdapter.submitList(photos)
        }
    }

    private suspend fun deletePhotoFromInternalStorage(fileName: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                deleteFile(fileName)
            } catch (e: IOException) {
                e.printStackTrace()
                false
            }
        }
    }

    private suspend fun loadPhotoFromInternalStorage(): List<InternalStoragePhoto> {
        return withContext(Dispatchers.IO) {
            val files = filesDir.listFiles()
            files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") }?.map {
                val bytes = it.readBytes()
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhoto(it.name, bmp)
            } ?: listOf()
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun savePhotoInInternalStorage(fileName: String, bmp: Bitmap): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                openFileOutput("$fileName.jpg", Context.MODE_PRIVATE).use { stream ->
                    if (!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                        throw IOException("Couldn't save bitmap.")
                    }
                }
                true
            } catch (e: IOException) {
                e.printStackTrace()
                false
            }
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    @SuppressLint("InlinedApi")
    private suspend fun savePhotoToExternalStorage(displayName: String, bmp: Bitmap): Boolean {
        return withContext(Dispatchers.IO) {
            val imageCollection = sdk29AndAbove {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "$displayName.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.WIDTH, bmp.width)
                put(MediaStore.Images.Media.HEIGHT, bmp.height)
            }

            try {
                contentResolver.insert(imageCollection, contentValues)?.also { uri ->
                    contentResolver.openOutputStream(uri).use { outputStream ->
                        if (!bmp.compress(Bitmap.CompressFormat.JPEG, 95, outputStream)) {
                            throw IOException("Couldn't save bitmap.")
                        }
                    }
                } ?: throw IOException("Couldn't create media store entry.")
                true
            } catch (e: IOException) {
                e.printStackTrace()
                false
            }
        }
    }

    @SuppressLint("InlinedApi")
    private suspend fun loadPhotoFromExternalStorage(): List<SharedStoragePhoto> {
        return withContext(Dispatchers.IO) {
            val collection = sdk29AndAbove {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val projection = arrayOf(
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.WIDTH,
                    MediaStore.Images.Media.HEIGHT)

            val photos = mutableListOf<SharedStoragePhoto>()

            contentResolver.query(
                    collection,
                    projection,
                    null,
                    null,
                    "${MediaStore.Images.Media.DISPLAY_NAME} ASC")?.use { cursor ->

                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH)
                val heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val displayName = cursor.getString(displayNameColumn)
                    val width = cursor.getInt(widthColumn)
                    val height = cursor.getInt(heightColumn)
                    val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    photos.add(SharedStoragePhoto(id, displayName, width, height, contentUri))
                }
                photos.toList()
            } ?: listOf()
        }
    }

    private suspend fun deletePhotoFromExternalStorage(photoUri: Uri) {
        withContext(Dispatchers.IO) {
            try {
                //for 28 and below
                contentResolver.delete(photoUri, null, null)
            } catch (e: SecurityException) {
                val intentSender = when {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                        //for 30
                        MediaStore.createDeleteRequest(contentResolver, listOf(photoUri)).intentSender
                    }
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                        //for 29
                        val recoverableSecurityException = e as? RecoverableSecurityException
                        recoverableSecurityException?.userAction?.actionIntent?.intentSender
                    }
                    else -> null
                }

                intentSender?.let { sender ->
                    intentSenderLauncher.launch(
                            IntentSenderRequest.Builder(sender).build()
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        contentResolver.unregisterContentObserver(contentObserver)
    }
}
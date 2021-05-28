package com.example.ganesh.deep_android.scopestorage.bean

import android.net.Uri

data class SharedStoragePhoto(
        val id: Long,
        val name: String,
        val width: Int,
        val height: Int,
        val contentUri: Uri
)
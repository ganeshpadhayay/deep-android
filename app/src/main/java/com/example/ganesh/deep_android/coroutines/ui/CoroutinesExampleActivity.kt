package com.example.ganesh.deep_android.coroutines.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ganesh.deep_android.R
import com.example.ganesh.deep_android.coroutines.data.TitleRepository
import com.example.ganesh.deep_android.coroutines.data.getDatabase
import com.example.ganesh.deep_android.coroutines.network.getNetworkService
import com.google.android.material.snackbar.Snackbar

class CoroutinesExampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines_example)

        val rootLayout: ConstraintLayout = findViewById(R.id.rootLayout)
        val title: TextView = findViewById(R.id.title)
        val taps: TextView = findViewById(R.id.taps)
        val spinner: ProgressBar = findViewById(R.id.spinner)

        // Get MainViewModel by passing a database to the factory
        val database = getDatabase(this)
        val repository = TitleRepository(getNetworkService(), database.titleDao)
        val viewModel = ViewModelProvider(this, CoroutinesExampleViewModel.FACTORY(repository)).get(CoroutinesExampleViewModel::class.java)

        // When rootLayout is clicked call onMainViewClicked in ViewModel
        rootLayout.setOnClickListener {
            viewModel.onMainViewClicked()
        }

        // update the title when the [MainViewModel.title] changes
        viewModel.title.observe(this, Observer { value ->
            value?.let {
                title.text = it
            }
        })


        viewModel.taps.observe(this, Observer { value ->
            taps.text = value
        })

        // show the spinner when [MainViewModel.spinner] is true
        viewModel.spinner.observe(this, Observer { value ->
            value.let { show ->
                spinner.visibility = if (show) View.VISIBLE else View.GONE
            }
        })

        // Show a snackbar whenever the [ViewModel.snackbar] is updated a non-null value
        viewModel.snackbar.observe(this, Observer { text ->
            text?.let {
                Snackbar.make(rootLayout, text, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarShown()
            }
        })
    }
}
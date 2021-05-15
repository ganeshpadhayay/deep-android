package com.example.ganesh.deep_android.databinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.ganesh.deep_android.R

class DataBindingExampleActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(DataBindingExampleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDataBindingExampleBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding_example)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }
}
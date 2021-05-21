package com.example.ganesh.deep_android

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.ganesh.deep_android.databinding.DataBindingExampleActivity
import com.example.ganesh.deep_android.ktx.KTXExampleActivity
import com.example.ganesh.deep_android.multithreading.MultiThreadingActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initClickListeners()
    }

    private fun initClickListeners() {
        button_data_binding.setOnClickListener(this)
        button_multithreading.setOnClickListener(this)
        button_kotlin_extensions.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_data_binding -> startActivity(Intent(this, DataBindingExampleActivity::class.java))
            R.id.button_multithreading -> startActivity(Intent(this, MultiThreadingActivity::class.java))
            R.id.button_kotlin_extensions -> startActivity(Intent(this, KTXExampleActivity::class.java))
        }
    }
}
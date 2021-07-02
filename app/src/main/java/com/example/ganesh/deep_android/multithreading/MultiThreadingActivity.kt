package com.example.ganesh.deep_android.multithreading

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.ganesh.deep_android.R
import com.example.ganesh.deep_android.multithreading.javamultithreading.JavaMultiThreadingActivity
import com.example.ganesh.deep_android.multithreading.mvvmcoroutines.ui.CoroutinesExampleActivity
import kotlinx.android.synthetic.main.activity_multi_threading.*

class MultiThreadingActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_threading)
        initClickListeners()
    }

    private fun initClickListeners() {
        button_coroutines_mvvm_example.setOnClickListener(this)
        button_java_multithreading.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_coroutines_mvvm_example -> startActivity(Intent(this, CoroutinesExampleActivity::class.java))
            R.id.button_java_multithreading -> startActivity(Intent(this, JavaMultiThreadingActivity::class.java))
        }
    }
}
package com.example.ganesh.deep_android.multithreading

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.ganesh.deep_android.R
import com.example.ganesh.deep_android.multithreading.coroutinesbasic.CoroutinesBasicActivity
import com.example.ganesh.deep_android.multithreading.mvvmcoroutines.ui.CoroutinesExampleActivity
import kotlinx.android.synthetic.main.activity_multi_threading.*

class MultiThreadingActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_threading)
        initClickListeners()
    }

    private fun initClickListeners() {
        coroutines_basics.setOnClickListener(this)
        button_coroutines_mvvm_example.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.coroutines_basics -> startActivity(Intent(this, CoroutinesBasicActivity::class.java))
            R.id.button_coroutines_mvvm_example -> startActivity(Intent(this, CoroutinesExampleActivity::class.java))
        }
    }
}
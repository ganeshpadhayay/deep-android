package com.example.ganesh.deep_android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ganesh.deep_android.coroutines.ui.CoroutinesExampleActivity
import com.example.ganesh.deep_android.databinding.DataBindingExampleActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_data_binding?.setOnClickListener {
            startActivity(Intent(this, DataBindingExampleActivity::class.java))
        }

        button_coroutines?.setOnClickListener {
            startActivity(Intent(this, CoroutinesExampleActivity::class.java))
        }
    }
}
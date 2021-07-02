package com.example.ganesh.deep_android.multithreading.javamultithreading

import android.os.*
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.ganesh.deep_android.R
import kotlinx.android.synthetic.main.activity_java_multi_threading.*

class JavaMultiThreadingActivity : AppCompatActivity() {

    private var isThreadStarted: Boolean = false
    private var isThreadStarted2: Boolean = false

    private var sampleThread: LooperThread? = null
    private var sampleThread2: LooperThread2? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_java_multi_threading)

        button?.setOnClickListener {
            if (!isThreadStarted) {
                startThread()
            } else {
                sendMessages(sampleThread as BaseThread)
            }
        }

        button2?.setOnClickListener {
            if (!isThreadStarted2) {
                startThread2()
            } else {
                sendMessages(sampleThread2 as BaseThread)
            }
        }
    }

    private fun startThread() {
        if (sampleThread == null || !sampleThread?.isAlive!!) {
            sampleThread = LooperThread("SampleThread")
            sampleThread?.start()
            isThreadStarted = true
        }
        sendMessages(sampleThread as BaseThread)
    }

    private fun startThread2() {
        if (sampleThread2 == null || !sampleThread2?.isAlive!!) {
            sampleThread2 = LooperThread2("SampleThread2")
            sampleThread2?.start()
            isThreadStarted2 = true
        }
        sendMessages(sampleThread2 as BaseThread)
    }

    private fun sendMessages(sampleThread: BaseThread) {
        sampleThread.sendMessage1("hello 1")
        sampleThread.sendMessage2("hello 2")
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onDestroy() {
        super.onDestroy()
        if (sampleThread?.isAlive!!) {
            sampleThread?.quitThread()
        }
        if (sampleThread2?.isAlive!!) {
            sampleThread2?.quitThread()
        }
    }
}

abstract class BaseThread : Thread() {
    abstract fun sendMessage1(text: String)
    abstract fun sendMessage2(text: String)
}

class LooperThread(private var threadName: String) : BaseThread() {

    private var mHandler: Handler? = null

    override fun run() {
        Looper.prepare()
        mHandler = Handler(Looper.myLooper()!!) { msg: Message ->
            when (msg.what) {
                1 -> {
                    //data1
                    val message: String = "message 1: " + msg.data.getString("key")
                    executeOnMainThread(threadName, message)
                }
                2 -> {
                    //data2
                    val message: String = "message 2: " + msg.data.getString("key")
                    executeOnMainThread(threadName, message)
                }
            }
            true
        }
        Looper.loop()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun quitThread() {
        mHandler?.looper?.quitSafely()
    }

    private fun executeOnMainThread(threadName: String, message: String) {
        Handler(Looper.getMainLooper()).post {
            Log.e("ganesh in Thread $threadName:", message)
        }
    }

    override
    fun sendMessage1(text: String) {
        mHandler?.sendMessageDelayed(createMessage1(text), 2000)
    }

    override
    fun sendMessage2(text: String) {
        mHandler?.sendMessageDelayed(createMessage2(text), 2000)
    }

    private fun createMessage1(text: String): Message {
        val message = Message()
        val bundle = Bundle()
        bundle.putString("key", text)
        message.data = bundle
        message.what = 1
        return message
    }

    private fun createMessage2(text: String): Message {
        val message = Message()
        val bundle = Bundle()
        bundle.putString("key", text)
        message.data = bundle
        message.what = 2
        return message
    }
}

class LooperThread2(private var threadName: String) : BaseThread() {

    private var mHandler: Handler? = null

    override fun run() {
        Looper.prepare()
        mHandler = Handler(Looper.myLooper()!!) { msg: Message ->
            when (msg.what) {
                1 -> {
                    //data1
                    val message: String = "message 1: " + msg.data.getString("key")
                    executeOnMainThread(threadName, message)
                }
                2 -> {
                    //data2
                    val message: String = "message 2: " + msg.data.getString("key")
                    executeOnMainThread(threadName, message)

                }
            }
            true
        }
        Looper.loop()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun quitThread() {
        mHandler?.looper?.quitSafely()
    }

    private fun executeOnMainThread(threadName: String, message: String) {
        Handler(Looper.getMainLooper()).post {
            Log.e("ganesh in Thread $threadName:", message)
        }
    }

    override
    fun sendMessage1(text: String) {
        mHandler?.sendMessageDelayed(createMessage1(text), 2000)
    }

    override
    fun sendMessage2(text: String) {
        mHandler?.sendMessageDelayed(createMessage2(text), 2000)
    }

    private fun createMessage1(text: String): Message {
        val message = Message()
        val bundle = Bundle()
        bundle.putString("key", text)
        message.data = bundle
        message.what = 1
        return message
    }

    private fun createMessage2(text: String): Message {
        val message = Message()
        val bundle = Bundle()
        bundle.putString("key", text)
        message.data = bundle
        message.what = 2
        return message
    }
}
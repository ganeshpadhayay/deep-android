package com.example.ganesh.deep_android.multithreading.coroutinesbasic.cancellation

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Ganesh Padhayay on 22/05/21.
 */
private fun main() = runBlocking {
    val job = launch {
        repeat(1000) { i ->
            println("job: I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin()    // cancels the job and waits for its completion
    println("main: Now I can quit.")
}
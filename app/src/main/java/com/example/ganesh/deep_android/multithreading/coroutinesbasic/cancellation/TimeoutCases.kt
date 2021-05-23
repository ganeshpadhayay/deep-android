package com.example.ganesh.deep_android.multithreading.coroutinesbasic.cancellation

import kotlinx.coroutines.*

/**
 * Created by Ganesh Padhayay on 22/05/21.
 */

//Timeout
/*
The TimeoutCancellationException that is thrown by withTimeout is a subclass of CancellationException.
We have not seen its stack trace printed on the console before. That is because inside a cancelled coroutine
CancellationException is considered to be a normal reason for coroutine completion.
However, in this example we have used withTimeout right inside the main function.
 */
//this will throw(Exception in thread "main" kotlinx.coroutines.TimeoutCancellationException: Timed out waiting for 1300 ms)
private fun main() = runBlocking {
    withTimeout(1300L) {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
}


//catch the exception
private fun main1() = runBlocking {
    try {
        withTimeout(1300L) {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }
    } catch (e: TimeoutCancellationException) {
        println(e.localizedMessage)
    } finally {
    }
}

//if you don't want to catch, instead want to receive a null result, use this
private fun main2() = runBlocking {
    val result = withTimeoutOrNull(1300L) {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
        "Done" // will get cancelled before it produces this result
    }
    println("Result is $result")
}


//Asynchronous timeout and resources
/*
The timeout event in withTimeout is asynchronous with respect to the code running in its block and may happen at any time,
even right before the return from inside of the timeout block. Keep this in mind if you open or acquire some resource inside
the block that needs closing or release outside of the block.

For example, here we imitate a closeable resource with the Resource class, that simply keeps track of how many times it was
 created by incrementing the acquired counter and decrementing this counter from its close function. Let us run a lot of
  coroutines with the small timeout try acquire this resource from inside of the withTimeout block after a bit of delay
  and release it from outside.
 */

var acquired = 0

class Resource {
    init {
        acquired++
    } // Acquire the resource

    fun close() {
        acquired--
    } // Release the resource
}

private fun main3() {
    runBlocking {
        repeat(100_000) { // Launch 100K coroutines
            launch {
                var resource: Resource? = null // Not acquired yet
                try {
                    withTimeout(60) { // Timeout of 60 ms
                        delay(50) // Delay for 50 ms
                        resource = Resource() // Store a resource to the variable if acquired
                    }
                    // We can do something else with the resource here
                } finally {
                    resource?.close() // Release the resource if it was acquired
                }
            }
        }
    }
    // Outside of runBlocking all coroutines have completed
    println(acquired) // Print the number of resources still acquired
}
package com.example.ganesh.deep_android.multithreading.coroutinesbasic.composing

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * Created by Ganesh Padhayay on 23/05/21.
 */

/**
 * Optionally, async can be made lazy by setting its start parameter to CoroutineStart.LAZY.
 * In this mode it only starts the coroutine when its result is required by await, or if its Job 's start function is invoked
 */
private fun main() = runBlocking {
    val time = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
        val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
        // some computation
        one.start() // start the first one
        two.start() // start the second one
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

private suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

private suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

/*
So, here the two coroutines are defined but not executed as in the previous example, but the control is given to the programmer on
when exactly to start the execution by calling start. We first start one, then start two, and then await for the individual coroutines
to finish.

Note that if we just call await in println without first calling start on individual coroutines, this will lead to sequential behavior,
since await starts the coroutine execution and waits for its finish, which is not the intended use-case for laziness.
The use-case for async(start = CoroutineStart.LAZY) is a replacement for the standard lazy function in cases when computation of the
value involves suspending functions.
 */
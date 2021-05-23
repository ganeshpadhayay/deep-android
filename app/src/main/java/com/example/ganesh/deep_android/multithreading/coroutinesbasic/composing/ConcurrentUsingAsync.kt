package com.example.ganesh.deep_android.multithreading.coroutinesbasic.composing

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * Created by Ganesh Padhayay on 23/05/21.
 */
//What if there are no dependencies between invocations of doSomethingUsefulOne and doSomethingUsefulTwo and we want to get the answer faster,
// by doing both concurrently? use Async

/*
Conceptually, async is just like launch. It starts a separate coroutine which is a light-weight thread that works concurrently with all the
 other coroutines. The difference is that launch returns a Job and does not carry any resulting value, while async returns a Deferred — a
 light-weight non-blocking future that represents a promise to provide a result later. You can use .await() on a deferred value to get its
 eventual result, but Deferred is also a Job, so you can cancel it if needed.
 */
private fun main() = runBlocking {
    val time = measureTimeMillis {
        val one = async { doSomethingUsefulThree() }
        val two = async { doSomethingUsefulFour() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

private suspend fun doSomethingUsefulThree(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 12
}

private suspend fun doSomethingUsefulFour(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

/**
This is twice as fast, because the two coroutines execute concurrently. Note that concurrency with coroutines is always explicit.
 */
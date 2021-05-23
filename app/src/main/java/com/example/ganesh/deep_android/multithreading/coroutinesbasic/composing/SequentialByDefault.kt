package com.example.ganesh.deep_android.multithreading.coroutinesbasic.composing

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * Created by Ganesh Padhayay on 22/05/21.
 */

//We use a normal sequential invocation, because the code in the coroutine, just like in the regular code, is sequential by default.
private fun main(args: Array<String>) = runBlocking {
    val time = measureTimeMillis {
        val one = doSomethingUsefulOne()
        if (one == 13) {
            val two = doSomethingUsefulTwo()
            println("The sum is ${one + two}")
        } else {
            println("The one is $one")
        }
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
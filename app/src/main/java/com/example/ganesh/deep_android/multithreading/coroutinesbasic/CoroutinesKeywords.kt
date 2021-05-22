package com.example.ganesh.deep_android.multithreading.coroutinesbasic

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Ganesh Padhayay on 22/05/21.
 */

/*
EXAMPLE 1: SIMPLE EXAMPLE OF runBlocking scope, launch, delay
fun main() = runBlocking {
    launch {
        printWorld()
    }
    println("Hello")
}

suspend fun printWorld() {
    delay(1000L)
    println("World!")
}
*/


/*
EXAMPLE 2: SIMPLE EXAMPLE OF runBlocking scope, custom scope, launch, delay
fun main() = runBlocking {
    println("coroutine context: " + this.coroutineContext + " thread: " + Thread.currentThread().name)
    printHelloWorld()
}

suspend fun printHelloWorld() = coroutineScope {
    println("coroutine context: " + this.coroutineContext + " thread: " + Thread.currentThread().name)
    launch {
        delay(1000L)
        println("World!")
    }
    println("Hello")
}
*/

/*
EXAMPLE 3: SIMPLE EXAMPLE OF runBlocking scope, custom scope, multiple launch, delay
fun main() = runBlocking {
    println("inside main scope, coroutine context: " + this.coroutineContext + " thread: " + Thread.currentThread().name)
    printHelloWorlds()
}

suspend fun printHelloWorlds() = coroutineScope {
    println("inside function scope, coroutine context: " + this.coroutineContext + " thread: " + Thread.currentThread().name)
    launch {
        println("inside coroutine 1, coroutine context: " + this.coroutineContext + " thread: " + Thread.currentThread().name)
        delay(1000)
        println("World1")
    }
    launch {
        println("inside coroutine 1, coroutine context: " + this.coroutineContext + " thread: " + Thread.currentThread().name)
        delay(1000)
        println("World2")
    }
    println("Hello")
}
*/

/*
EXAMPLE 4: SIMPLE EXAMPLE OF runBlocking scope, custom scope, job, yield, join
fun main() = runBlocking {
    // launch a new coroutine and keep a reference to its Job
    val job = launch {
        delay(1000L)
        println("World!")
    }
    println("Hello")
    job.join() // waits until all child coroutines under this job scope complete
    println("Done")
}
*/


//Coroutines ARE light-weight
fun main() = runBlocking {
    repeat(100_00) {
        // launch a lot of coroutines
        launch {
            delay(5000L)
            print(".")
        }
    }
}


//this will give (Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread)
/*
fun main() {
    for (i in 1..10000) {
        thread {
            Thread.sleep(5000L)
            print(".")
        }
    }
}
*/
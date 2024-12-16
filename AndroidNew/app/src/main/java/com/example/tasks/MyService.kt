package com.example.tasks

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyService : Service() {

        private var mBinder : IBinder = LocalBinder()

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    fun countPi(poins : Int) : Double {

        var poinsInCircle = 0

        for (i in 0 until poins) {
            val x = Math.random() * 2 - 1
            val y = Math.random() * 2 - 1

            if(x * x + y * y <= 1) {
                poinsInCircle += 1
            }
        }

        return 4.0 * poinsInCircle / poins
    }

    fun fibonacci(n: Int, callback: (List<Int>) -> Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            val sequence = fibonacciSequence(n)
            withContext(Dispatchers.Main) {
                callback(sequence)
            }
        }
    }


    private fun fibonacciSequence(n: Int): List<Int> {
        val sequence = mutableListOf<Int>()
        for (i in 0 until n) {
            sequence.add(fibonacciRecursive(i))
        }
        return sequence
    }

    private fun fibonacciRecursive(n: Int): Int {
        return if (n <= 1) {
            n
        } else {
            fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2)
        }
    }





    inner class LocalBinder : Binder() {

        fun getService() : MyService {
            return this@MyService
        }
    }

}
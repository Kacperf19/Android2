package com.example.tasks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tasks.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "msg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.textView1.text = ""
        binding.textView2.text = ""
        binding.textView3.text = ""
        binding.progressBar.visibility = View.INVISIBLE

        binding.button1.setOnClickListener{
            var tv1 = binding.textView1

            if (tv1.text.isEmpty()) {
                tv1.text = "Zadanie 1"
            } else {
                tv1.text = ""
            }
        }
        binding.button2.setOnClickListener{
            binding.textView2.text = ""
            var result : String = ""
            runBlocking {
                launch {
                     result = doTask2()
                    binding.textView2.text = result
                }

            }
        }
        binding.button3.setOnClickListener{
            CoroutineScope(Dispatchers.Main).launch {
                binding.textView3.text = ""
                binding.button3.isEnabled = false
                binding.progressBar.visibility = View.VISIBLE
                var result = doTask3()
                binding.button3.isEnabled = true
                binding.textView3.text = "Wynik: $result"
                binding.progressBar.visibility = View.INVISIBLE
            }
        }
        binding.button4.setOnClickListener{
            startActivity(
                Intent(
                    this@MainActivity,
                    SecondActivity::class.java
                )
            )
        }
    }

    private suspend fun doTask2() : String {
        Log.d(TAG, "Początek zadania 1")
        delay(5000)
        Log.d(TAG,"Koniec zadania")
        return "zadanie 1 zostało wykonane"
    }
    private suspend fun doTask3() : Int {
        Log.d(TAG, "Początek zadania 2")
        delay(8000)
        Log.d(TAG,"Koniec zadania")
        return (0..100).random()
    }

}
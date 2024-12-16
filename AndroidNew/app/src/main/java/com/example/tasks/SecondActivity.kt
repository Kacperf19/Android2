package com.example.tasks

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tasks.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private var points : Int = 0
    private var result : Double = 0.0
    private var mBound = false
    private var pole : Int = 0
private var wynik : String = " "


    var myService : MyService? = null

    var myConnection : ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            val binder : MyService.LocalBinder = service as MyService.LocalBinder
            myService = binder.getService()
            mBound = true
            Toast.makeText(applicationContext,"Serwis podłączony" , Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
          myService = null
            mBound = false
            Toast.makeText(applicationContext,"Serwis odłączony" , Toast.LENGTH_SHORT).show()
        }

    }

    override fun onStart() {
        super.onStart()
        if (!mBound) {
            this.bindService(
                Intent(this, MyService::class.java),
                myConnection,
                Context.BIND_AUTO_CREATE

            )
        }
    }
    override fun onStop() {
        super.onStop()
        if(mBound) {
            this.unbindService(myConnection)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.result1TV.text = ""
        binding.number1ET.setText("0")
        binding.resaultF.text = ""
        binding.NumberF.setText("0")

        binding.count1BTN.setOnClickListener{
            points = Integer.parseInt( binding.number1ET.text.toString())
            result = myService?.countPi(points) ?: 0.0
            binding.result1TV.text = "Pi = $result"
        }

        binding.buttonF.setOnClickListener{
            binding.buttonF.isEnabled = false


                val input = binding.NumberF.text.toString()

                if (input.isNotEmpty()) {
                    val n = input.toIntOrNull()
                    if (n != null) {
                        myService?.fibonacci(n+1) { sequence ->
                            binding.resaultF.text = sequence.joinToString(", ")
                        }
                    } else {
                        binding.resaultF.text = "Wprowadź liczbę!"
                    }
                } else {
                    binding.resaultF.text = "Wprowadź liczbę!"
                }

            }
        binding.buttonF.isEnabled = true
    }
}
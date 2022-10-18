package com.example.simondice

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    var secuencia = ArrayList<String>() //Secuncia en ronda actual
    var ronda = 0     //número de ronda
    var numero = 4    //número de luces encendidas
    var restante = 0  //número de comprobaciones restantes


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bRed: Button = findViewById(R.id.red)
        val bBlue: Button = findViewById(R.id.blue)
        val bYellow: Button = findViewById(R.id.yellow)
        val bGreen: Button = findViewById(R.id.green)
        val binit: Button = findViewById(R.id.start)

        bRed.setOnClickListener {
            comprobar("red")
        }
        bBlue.setOnClickListener {
            comprobar("blue")
        }
        bYellow.setOnClickListener {
            comprobar("Yellow")
        }
        bGreen.setOnClickListener {
            comprobar("green")
        }
        binit.setOnClickListener {
            iniciarPartida()
        }

    }

    private fun iniciarPartida() {
        mostrarSecuencia()
    }

    private fun generarSecuencia() {
        val randomInt = (1..4).random()
        when (randomInt) {
            1 -> R.drawable.red_button
            2 -> R.drawable.red_button
            3 -> R.drawable.red_button
            else -> R.drawable.red_button
        }
    }

    private fun mostrarSecuencia() {

    }

    private fun comprobar(color: String) {
        when (color) {
            "red" -> {

            }
            "blue" -> {

            }
            "yellow" -> {

            }
            else -> {

            }
        }

    }
}
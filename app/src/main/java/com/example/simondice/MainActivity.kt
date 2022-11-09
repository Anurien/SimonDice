package com.example.simondice

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var secuencia = ArrayList<String>() //Secuencia en ronda actual
    private var comprobar = ArrayList<String>() //Secuencia de comprobacion
    private var ronda = 1     //número de ronda
    private var numero = 1    //número de luces encendidas
    private lateinit var bRed: Button
    private lateinit var bYellow: Button
    private lateinit var bBlue: Button
    private lateinit var bGreen: Button
    private lateinit var binit: Button


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

        bRed = findViewById(R.id.red)
        bBlue = findViewById(R.id.blue)
        bYellow = findViewById(R.id.yellow)
        bGreen = findViewById(R.id.green)
        binit = findViewById(R.id.start)

        bRed.setOnClickListener {
            comprobar("rojo")
        }
        bBlue.setOnClickListener {
            comprobar("azul")
        }
        bYellow.setOnClickListener {
            comprobar("amarillo")
        }
        bGreen.setOnClickListener {
            comprobar("verde")
        }
        binit.setOnClickListener {
            iniciarPartida()
        }

    }

    private fun iniciarPartida() {
        mostrarSecuencia(numero)
    }

    /**
     * Muesta una secuencia de parpadeos
     * @numero: número de parpadeos que se van a realizar
     */
    private fun mostrarSecuencia(numero: Int) {

        var encendido = 0L

        for (i in 1..numero) {
            val random = Random().nextInt(4)
            encendido += 750L
            when (random) {
                0 -> {
                    parpadeo(encendido, "azul", numero, i)
                    secuencia.add("azul")
                }
                1 -> {
                    parpadeo(encendido, "amarillo", numero, i)
                    secuencia.add("amarillo")
                }
                2 -> {
                    parpadeo(encendido, "verde", numero, i)
                    secuencia.add("verde")
                }
                3 -> {
                    parpadeo(encendido, "rojo", numero, i)
                    secuencia.add("rojo")
                }
            }
        }
        comprobar = secuencia
    }

    /**
     * Realiza el parpadeo del botón @color
     * @encendido: Milisegundos de la secuenda a los que se va a encender la luz
     * @color: Color que se va a encender
     * @maximo: número de luces totales que se van a encender
     * @actual: número de luz actual que se va a encender respecto a @maximo de luces que se encienden
     */
    @OptIn(DelicateCoroutinesApi::class)
    private fun parpadeo(encendido: Long, color: String, maximo: Int, actual: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            delay(encendido)
            when (color) {
                "azul" -> {
                    bBlue.setBackgroundResource(R.drawable.blue_on)
                    delay(500L)
                    bBlue.setBackgroundResource(R.drawable.blue_button)
                }
                "amarillo" -> {
                    bYellow.setBackgroundResource(R.drawable.yellow_on)
                    delay(500L)
                    bYellow.setBackgroundResource(R.drawable.yellow_button)
                }
                "verde" -> {
                    bGreen.setBackgroundResource(R.drawable.green_on)
                    delay(500L)
                    bGreen.setBackgroundResource(R.drawable.green_button)
                }
                "rojo" -> {
                    bRed.setBackgroundResource(R.drawable.red_on)
                    delay(500L)
                    bRed.setBackgroundResource(R.drawable.red_button)
                }
            }
            if (actual >= maximo) {
                Toast.makeText(this@MainActivity, "Reproduce la secuencia", Toast.LENGTH_SHORT)
                    .show()

            }
        }
    }

    /**
     * Comprueba si el usuario, a la hora de pulsar un botón, ha acertado con la siguiente luz de la secuencia.
     * Realiza una serie de operaciones según haya acertado, haya fallado o no queden comprobaciones restantes.
     * @color: Botón que pulsó el usuario
     */
    @SuppressLint("SetTextI18n")
    private fun comprobar(color: String) {
        if (comprobar.isEmpty()) {
            ronda = 1
            numero = 1

            Toast.makeText(this@MainActivity, "Presiona Play para jugar", Toast.LENGTH_SHORT)
                .show()
            secuencia.clear()
            comprobar.clear()
        } else {
            if (comprobar[0] == color) {
                comprobar.removeAt(0)
                Toast.makeText(this@MainActivity, "Acierto", Toast.LENGTH_SHORT).show()
                if (comprobar.isEmpty()) {
                    numero++
                    ronda++
                    runBlocking {
                        Toast.makeText(this@MainActivity, "Ronda: $ronda", Toast.LENGTH_SHORT)
                            .show()
                    }
                    val rond: TextView = findViewById(R.id.ronda)
                    rond.text = "Ronda: $ronda"
                    mostrarSecuencia(numero)
                }
            } else {
                ronda = 1
                numero = 1

                Toast.makeText(
                    this@MainActivity,
                    "Ohhh...has fallado,vuelve a intentarlo",
                    Toast.LENGTH_SHORT
                ).show()
                val rond: TextView = findViewById(R.id.ronda)
                rond.text = "Ronda: $ronda"
                secuencia.clear()
                comprobar.clear()
            }
        }
    }
}
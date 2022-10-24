package com.example.simondice

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.coroutines.runBlocking
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
        mostrarSecuencia(numero)
    }

    private fun generarSecuencia() {
        val randomInt = (1..4).random()
        when (randomInt) {
            1 -> R.drawable.red_button
            2 -> R.drawable.green_button
            3 -> R.drawable.blue_button
            else -> R.drawable.yellow_button
        }
    }

    /**
     * Muesta una secuencia de parpadeos
     * @numero: número de parpadeos que se van a realizar
     */
    private fun mostrarSecuencia(numero: Int) {

        var encendido = 0L

        //deshabilitarBotones()

        for (i in 1..numero) {
            val random = Random().nextInt(4)
            encendido += 2500L
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
    }

    private fun parpadeo(encendido: Long, color: String, maximo: Int, actual: Int) {
        when (color) {
            "azul" -> {

            }
            "amarillo" -> {

            }
            "verde" -> {

            }
            "rojo" -> {

            }
        }
    }

        /**
         * Comprueba si el usuario, a la hora de pulsar un botón, ha acertado con la siguiente luz de la secuencia.
         * Realiza una serie de operaciones según haya acertado, haya fallado o no queden comprobaciones restantes.
         * @color: Botón que pulsó el usuario
         */
        private fun comprobar(color: String) {
            if (secuencia.isEmpty() == true) {
                ronda = 1
                numero = 3
                restante = 0
                Toast.makeText(this@MainActivity, "Presiona Play para jugar", Toast.LENGTH_SHORT)
                    .show()
                secuencia.clear()
            } else {
                if (secuencia.get(0).equals(color)) {
                    secuencia.removeAt(0)
                    Toast.makeText(this@MainActivity, "Acierto", Toast.LENGTH_SHORT).show()
                    restante--
                    if (secuencia.isEmpty() == true) {
                        numero++
                        ronda++
                        runBlocking {
                            Toast.makeText(this@MainActivity, "Ronda: $ronda", Toast.LENGTH_SHORT)
                                .show()
                        }
                        mostrarSecuencia(numero)
                    }
                } else {
                    ronda = 1
                    numero = 3
                    restante = 0
                    Toast.makeText(
                        this@MainActivity,
                        "Ohhh...has fallado,vuelve a intentarlo",
                        Toast.LENGTH_SHORT
                    ).show()
                    secuencia.clear()
                }
            }
        }
    }
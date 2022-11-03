package com.example.simondice

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import kotlinx.coroutines.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var secuencia = ArrayList<String>() //Secuncia en ronda actual
    var ronda = 0     //número de ronda
    var numero = 1    //número de luces encendidas
    var restante = 0  //número de comprobaciones restantes
    lateinit var bRed: Button;
    lateinit var bYellow: Button;
    lateinit var bBlue: Button;
    lateinit var bGreen: Button;
    lateinit var binit: Button;


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
            encendido += 1000L
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
        numero+1
    }

    /**
     * Realiza el parpadeo del botón @color
     * @encendido: Milisegundos de la secuenda a los que se va a encender la luz
     * @color: Color que se va a encender
     * @maximo: número de luces totales que se van a encender
     * @actual: número de luz actual que se va a encender respecto a @maximo de luces que se encienden
     */
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
                restante = numero

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
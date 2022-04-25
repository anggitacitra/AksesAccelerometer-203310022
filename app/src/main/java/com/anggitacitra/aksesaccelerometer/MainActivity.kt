package com.anggitacitra.aksesaccelerometer

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener { //Step 1 : implement sensor event listener
    private lateinit var sensorManager: SensorManager
    private lateinit var square: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        square = findViewById(R.id.accelero) // memanggil text view sebagai kotak yang ada pada activity_main.xml

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager //Step 3 : menyiapkan sensor manager
        //Step 4 : memilih sensor accelerometer untuk digunakan
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }
    // Step 2 : abstraksi dari method onSensorChanged
    override fun onSensorChanged(event: SensorEvent?) {
        //Step 5 : menentukan sensor tipe accelerometer yang digunakan
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0] // memberikan nilai x
            val y = event.values[1] // memberikan nilai y
            square.apply {
                rotationX = x * 3f
                rotationY = y * 3f
                rotation = -y
                translationX = y * -10 // translete nilai y maximal -10
                translationY = x * 10 // translete nilai x maximal 10
            }
            //Step 6 : mengubah warna kotak ketika posisi nilai 0 dan tidak 0
            val color = if (x.toInt() == 0 && y.toInt() == 0) Color.GREEN else Color.DKGRAY
            square.setBackgroundColor(color)
            square.text =
                "nilai x : ${x.toInt()}\nnilai y : ${y.toInt()}"
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }
    //Step 7 : menghapus sensor manager ketika aplikasi dimatikan
    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }
}
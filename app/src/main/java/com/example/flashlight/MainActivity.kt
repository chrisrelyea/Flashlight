package com.example.flashlight

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {
    private lateinit var toggleLight: SwitchMaterial
    private var cameraManager: CameraManager? = null
    private var getCameraId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toggleLight = findViewById(R.id.switch1)
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        toggleLight.setOnCheckedChangeListener{ _, _ ->
        toggleFlashlight()
        }
        getCameraId = cameraManager!!.cameraIdList[0]
    }

    // Need to add error checking - phone doesnt have a camera, phone is outdated and cannot
    // control flash, etc.

    private fun toggleFlashlight() {
        if (toggleLight.isChecked) {
            cameraManager!!.setTorchMode(getCameraId!!, true)
            Toast.makeText(this, "Flashlight On", Toast.LENGTH_SHORT).show()
        }
        else {
            cameraManager!!.setTorchMode(getCameraId!!, false)
            Toast.makeText(this, "Flashlight Off", Toast.LENGTH_SHORT).show()

        }

    }



}
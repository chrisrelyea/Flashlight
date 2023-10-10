package com.example.flashlight

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {
    private lateinit var toggleLight: SwitchMaterial
    private var cameraManager: CameraManager? = null
    private var getCameraId: String? = null

    //Mak
    private lateinit var enterButton: Button
    private lateinit var enterText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toggleLight = findViewById(R.id.switch1)
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        //Mak
        enterButton = findViewById(R.id.enter_button)
        enterText = findViewById(R.id.enter_action)

        toggleLight.setOnCheckedChangeListener{ _, _ ->
        toggleFlashlight()
        }
        getCameraId = cameraManager!!.cameraIdList[0]

        //Mak
        enterButton.setOnClickListener {
            if (enterText.text.toString() == "on") {
                toggleLight.isChecked = true
                toggleFlashlight()
            } else if (enterText.text.toString() == "off") {
                toggleLight.isChecked = false
                toggleFlashlight()
            } else
                Toast.makeText(this, "Please type a VALID action: on/off", Toast.LENGTH_SHORT).show()
            enterText.text.clear()
        }
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
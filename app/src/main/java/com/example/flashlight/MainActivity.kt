package com.example.flashlight

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.View
import android.util.Log
import kotlin.math.abs
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.view.MotionEvent

import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    private lateinit var gestureDetector: GestureDetector
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
        gestureDetector = GestureDetector(this)
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        //Mak
        enterButton = findViewById(R.id.enter_button)
        enterText = findViewById(R.id.enter_action)

        //Mak check if does not support flashlight
        var hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
        if (!hasFlash)
            Toast.makeText(this, "Oops! Flashlight not available! Please turn this", Toast.LENGTH_SHORT).show()

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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (gestureDetector.onTouchEvent(event)) {
            true
        }
        else {
            super.onTouchEvent(event)
        }
    }


    override fun onDown(p0: MotionEvent): Boolean {
        return false
    }

    override fun onShowPress(p0: MotionEvent) {
        return
    }

    override fun onSingleTapUp(p0: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent) {
        return
    }


    // When a fling is detected, some geometry is used to detect swipe direction
    // This direction of the swipe is stored as "direction" variable
    override fun onFling(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        Log.d("test", "Fling Detected!")
        Log.d("vertical speed", p3.toString())


        var x1 = p0.x
        var y1 = p0.y
        var x2 = p1.x
        var y2 = p1.y
        var diffY = y2-y1
        var diffX = x2-x1
        var direction = ""
        var swipeThreshold = 200

        var swipeDistance = abs(diffY)
        direction = if (abs(diffY) > abs(diffX)) {
            if (y2 >= y1) {
                "DOWN"
            } else {
                "UP"
            }
        } else {
            if (x1 >= x2) {
                "LEFT"
            } else {
                "RIGHT"
            }
        }
        Log.d("direction", direction)
        var result = false
        if (direction == "UP" && swipeDistance > swipeThreshold && abs(p3).toInt() == 8000){
            toggleLight.isChecked = true
            toggleFlashlight()

        }
        else if (direction == "DOWN" && swipeDistance > swipeThreshold && abs(p3).toInt() == 8000) {
            toggleLight.isChecked = false
            toggleFlashlight()
        }



        return true

    }
}



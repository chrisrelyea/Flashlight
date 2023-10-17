package com.example.flashlight

import android.hardware.camera2.CameraManager
import android.view.GestureDetector
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModel
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlin.math.abs

class ViewModels: ViewModel() {
    private lateinit var gestureDetector: GestureDetector
    private lateinit var toggleLight: SwitchMaterial
    var cameraManager: CameraManager? = null
    var getCameraId: String? = null
    private lateinit var enterButton: Button
    private lateinit var enterText: EditText
    var x1 = 0
    var y1 = 0
    var x2 = 0
    var y2 = 0
    var diffY = y2-y1
    var diffX = x2-x1
    var direction = ""
    var swipeThreshold = 200
    var swipeDistance = abs(diffY)
}
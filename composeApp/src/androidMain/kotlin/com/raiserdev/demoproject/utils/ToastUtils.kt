package com.raiserdev.demoproject.utils

import android.widget.Toast
import com.raiserdev.demoproject.AndroidContextHolder

actual fun showToast(message: String) {
    val context = AndroidContextHolder.appContext
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
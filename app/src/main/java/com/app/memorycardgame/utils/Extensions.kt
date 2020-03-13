package com.app.memorycardgame.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast

fun Any.objectScopeName() = "${javaClass.simpleName}_${hashCode()}"

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.vibrate(milliseconds:Long = 500){
    val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    val canVibrate:Boolean = vibrator.hasVibrator()

    if(canVibrate){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    milliseconds,
                    // The default vibration strength of the device.
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }else{
            vibrator.vibrate(milliseconds)
        }
    }
}
package uk.co.bradreed.shitgame

import android.graphics.Bitmap
import java.util.*

fun Bitmap.scaleToWidth(newWidth: Int): Bitmap {
    val scaleFactor = newWidth.toFloat() / width.toFloat()

    val newHeight = (height * scaleFactor).toInt()

    return Bitmap.createScaledBitmap(this, newWidth, newHeight, false)
}

fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) +  start

fun ClosedRange<Double>.random() = start + (endInclusive - start) * Random().nextDouble()
package uk.co.bradreed.trolleygame.objects

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.util.DisplayMetrics
import uk.co.bradreed.trolleygame.GameObject
import uk.co.bradreed.trolleygame.R
import uk.co.bradreed.trolleygame.structs.Point

class Trolley(private val rightFacingBitmap: Bitmap, var location: Point) : GameObject {

    companion object {
        const val DRAWABLE = R.drawable.trolley
    }

    enum class Direction {
        RIGHT,
        LEFT
    }

    private val leftFacingBitmap = Bitmap.createBitmap(
            rightFacingBitmap,
            0,
            0,
            rightFacingBitmap.width,
            rightFacingBitmap.height,
            Matrix().apply { preScale(-1f, 1f) },
            false
    ).apply { density = DisplayMetrics.DENSITY_DEFAULT }

    private var direction = Direction.RIGHT

    private val currentBitmap get() = when (direction) {
        Direction.RIGHT -> rightFacingBitmap
        Direction.LEFT -> leftFacingBitmap
    }

    val width get() = currentBitmap.width

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(currentBitmap, location.x.toFloat(), location.y.toFloat(), null)
    }

    fun moveTo(x: Int) {
        direction = when {
            x < location.x -> Direction.LEFT
            x > location.x -> Direction.RIGHT
            else -> direction
        }

        location = location.copy(x = x)
    }
}
package uk.co.bradreed.shitgame.objects

import android.graphics.Bitmap
import android.graphics.Canvas
import uk.co.bradreed.shitgame.GameObject
import uk.co.bradreed.shitgame.R
import uk.co.bradreed.shitgame.structs.Point

class Trolley(private val forwardsBitmap: Bitmap,
              private val backwardsBitmap: Bitmap,
              var location: Point) : GameObject {

    companion object {
        const val DRAWABLE = R.drawable.trolley
    }

    enum class Direction {
        FORWARDS,
        BACKWARDS
    }

    private var direction = Direction.FORWARDS

    private val currentBitmap get() = when (direction) {
        Direction.FORWARDS -> forwardsBitmap
        Direction.BACKWARDS -> backwardsBitmap
    }

    val width get() = forwardsBitmap.width

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(currentBitmap, location.x.toFloat(), location.y.toFloat(), null)
    }

    fun moveTo(x: Int) {
        direction = when {
            x < location.x -> Direction.BACKWARDS
            x > location.x -> Direction.FORWARDS
            else -> direction
        }

        location = location.copy(x = x)
    }
}
package uk.co.bradreed.shitgame.objects

import android.graphics.Bitmap
import android.graphics.Canvas
import uk.co.bradreed.shitgame.*
import java.lang.Math.pow
import java.lang.Math.sqrt

class Orange(private val gameSurface: GameSurface, startingPoint: Point) : GameObject(startingPoint) {
    companion object {
        const val DRAWABLE = R.drawable.orange
        lateinit var bitmap: Bitmap
    }

    override val movingVector = Point(0, 1)

    private val velocity = (0.25..0.5).random()

    override fun update() {
        val now = System.nanoTime()

        // First draw
        if (lastDrawNanoTime == -1L) {
            lastDrawNanoTime = now
        }

        // Nano -> millis
        val deltaTime = (now - lastDrawNanoTime) / 1000000

        val distance = velocity * deltaTime

        val movingVectorLength = sqrt(pow(movingVector.x.toDouble(), 2.0) + pow(movingVector.y.toDouble(), 2.0))

        location = Point(
                x = location.x + (distance * movingVector.x / movingVectorLength).toInt(),
                y = location.y + (distance * movingVector.y / movingVectorLength).toInt()
        )

        if (isAtBottom) {
            destroyMe = true
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, location.x.toFloat(), location.y.toFloat(), null)
        lastDrawNanoTime = System.nanoTime()
    }

    private val isAtBottom get() = location.y >= gameSurface.height - bitmap.height
}
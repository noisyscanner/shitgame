package uk.co.bradreed.trolleygame.food

import android.graphics.Bitmap
import android.graphics.Canvas
import uk.co.bradreed.trolleygame.GameObject
import uk.co.bradreed.trolleygame.random
import uk.co.bradreed.trolleygame.structs.Point
import uk.co.bradreed.trolleygame.structs.Vector

abstract class FoodItem(val bitmap: Bitmap, var location: Point) : GameObject {

    abstract val value: Int

    protected val velocity = (0.5..0.75).random()
    protected val movingVector = Vector(0, 1)

    protected var lastDrawNanoTime: Long = -1L

    protected open fun getTimeNow() = System.nanoTime()

    var destroyMe = false

    fun update() {
        val now = getTimeNow()

        if (lastDrawNanoTime == -1L) {
            lastDrawNanoTime = now
        }

        val deltaTimeMillis = (now - lastDrawNanoTime) / 1000000
        val distanceTravelled = velocity * deltaTimeMillis

        location = getNextLocation(distanceTravelled)
    }

    override fun draw(canvas: Canvas) {
        if (destroyMe) return

        canvas.drawBitmap(bitmap, location.x.toFloat(), location.y.toFloat(), null)
        lastDrawNanoTime = getTimeNow()
    }

    private fun getNextLocation(distanceTravelled: Double) = location + Vector(
            dx = (distanceTravelled * movingVector.dx / movingVector.length).toInt(),
            dy = (distanceTravelled * movingVector.dy / movingVector.length).toInt()
    )
}
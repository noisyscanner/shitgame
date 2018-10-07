package uk.co.bradreed.trolleygame.food

import android.graphics.Bitmap
import android.graphics.Canvas
import uk.co.bradreed.trolleygame.GameObject
import uk.co.bradreed.trolleygame.GameSurface
import uk.co.bradreed.trolleygame.random
import uk.co.bradreed.trolleygame.structs.Point
import uk.co.bradreed.trolleygame.structs.Vector

abstract class FoodItem(private var gameSurface: GameSurface,
                        private val bitmap: Bitmap,
                        private var location: Point) : GameObject {

    abstract val value: Int

    protected val velocity = (0.5..0.75).random()
    protected val movingVector = Vector(0, 1)

    private var lastDrawNanoTime: Long = -1L

    var destroyMe = false

    fun update() {
        val now = System.nanoTime()

        if (lastDrawNanoTime == -1L) {
            lastDrawNanoTime = now
        }

        // Nano -> millis
        val deltaTime = (now - lastDrawNanoTime) / 1000000

        val distanceTravelled = velocity * deltaTime

        location = getNextLocation(distanceTravelled)
    }

    override fun draw(canvas: Canvas) {
        if (destroyMe) return

        if (isCaught) {
            gameSurface.onCatchFruit()
            destroyMe = true
            return
        }

        if (isAtBottom) {
            gameSurface.onDropFruit()
            destroyMe = true
            return
        }

        canvas.drawBitmap(bitmap, location.x.toFloat(), location.y.toFloat(), null)
        lastDrawNanoTime = System.nanoTime()
    }

    private val isAtBottom get() = location.y >= gameSurface.height - bitmap.height

    private val isCaught: Boolean
        get() {
            val trolleyWidth = gameSurface.trolley.width
            val trolleyLoc = gameSurface.trolley.location

            return location.y >= trolleyLoc.y - bitmap.height &&
                    location.x >= trolleyLoc.x &&
                    location.x <= trolleyLoc.x + trolleyWidth - bitmap.width
        }

    private fun getNextLocation(distanceTravelled: Double) = location + Point(
            x = (distanceTravelled * movingVector.dx / movingVector.length).toInt(),
            y = (distanceTravelled * movingVector.dy / movingVector.length).toInt()
    )
}
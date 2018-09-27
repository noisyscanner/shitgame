package uk.co.bradreed.shitgame.fruit

import android.graphics.Bitmap
import android.graphics.Canvas
import uk.co.bradreed.shitgame.GameObject
import uk.co.bradreed.shitgame.GameSurface
import uk.co.bradreed.shitgame.structs.Point

abstract class Fruit(private var gameSurface: GameSurface,
                     private val bitmap: Bitmap,
                     protected var location: Point) : GameObject {
    abstract val velocity: Double

    private var lastDrawNanoTime: Long = -1L

    override var destroyMe = false

    override fun update() {
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
        if (!destroyMe) {
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
        } else return

        canvas.drawBitmap(bitmap, location.x.toFloat(), location.y.toFloat(), null)
        lastDrawNanoTime = System.nanoTime()
    }

    private val isAtBottom get() = location.y >= gameSurface.height - bitmap.height

    private val isCaught: Boolean
        get() {
            val trolleyLoc = gameSurface.trolley.location

            return location.y >= trolleyLoc.y - bitmap.height &&
                    location.x >= trolleyLoc.x &&
                    location.x <= trolleyLoc.x + bitmap.width - bitmap.width
        }

    abstract fun getNextLocation(distanceTravelled: Double): Point
}
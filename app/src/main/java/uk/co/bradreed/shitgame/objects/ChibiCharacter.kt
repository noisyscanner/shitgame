/*package uk.co.bradreed.shitgame.objects

import android.graphics.Bitmap
import android.graphics.Canvas
import uk.co.bradreed.shitgame.GameObject
import uk.co.bradreed.shitgame.GameObject.Direction.*
import uk.co.bradreed.shitgame.GameSurface
import uk.co.bradreed.shitgame.Point
import java.lang.Math.*

class ChibiCharacter(private val gameSurface: GameSurface, image: Bitmap, x: Int, y: Int) : GameObject(image, 4, 3, x, y) {
    companion object {
        private const val VELOCITY = 1f
    }

    private var rowUsing = ROW_LEFT_TO_RIGHT
    private var colUsing = 0

    private var leftToRights = Array(colCount) { i -> this.createSubImageAt(ROW_LEFT_TO_RIGHT.value, i) }
    private var rightToLefts = Array(colCount) { i -> this.createSubImageAt(ROW_RIGHT_TO_LEFT.value, i) }
    private var topToBottoms = Array(colCount) { i -> this.createSubImageAt(ROW_TOP_TO_BOTTOM.value, i) }
    private var bottomToTops = Array(colCount) { i -> this.createSubImageAt(ROW_BOTTOM_TO_TOP.value, i) }

    override val movingVector: Point = Point(0, 20)

    private val currentMoveBitmap: Bitmap
        get() = when (rowUsing) {
            ROW_BOTTOM_TO_TOP -> bottomToTops
            ROW_LEFT_TO_RIGHT -> leftToRights
            ROW_RIGHT_TO_LEFT -> rightToLefts
            ROW_TOP_TO_BOTTOM -> topToBottoms
        }[colUsing]

    override fun update() {
        colUsing++
        if (colUsing >= colCount) {
            colUsing = 0
        }

        val now = System.nanoTime()

        // First draw
        if (lastDrawNanoTime == -1L) {
            lastDrawNanoTime = now
        }

        // Nano -> millis
        val deltaTime = (now - lastDrawNanoTime) / 1000000

        val distance = VELOCITY * deltaTime

        val movingVectorLength = sqrt(pow(movingVector.x.toDouble(), 2.0) + pow(movingVector.y.toDouble(), 2.0))

        // Set new position of character
        x += (distance * movingVector.x / movingVectorLength).toInt()
        y += (distance * movingVector.y / movingVectorLength).toInt()

        // When the game's character touches the edge of the screen, then change direction
        if (x < 0) {
            x = 0
            movingVector.x = -movingVector.x
        } else if (x > gameSurface.width - width) {
            x = gameSurface.width - width
            movingVector.x = -movingVector.x
        }

        if (y < 0) {
            y = 0
            movingVector.y = -movingVector.y
        } else if (y > gameSurface.height - height) {
            y = gameSurface.height - height
            movingVector.y = -movingVector.y
        }

        rowUsing = if (movingVector.x > 0) {
            if (movingVector.y > 0 && abs(movingVector.x) < abs(movingVector.y)) {
                ROW_TOP_TO_BOTTOM
            } else if (movingVector.y < 0 && abs(movingVector.x) < abs(movingVector.y)) {
                ROW_BOTTOM_TO_TOP
            } else {
                ROW_LEFT_TO_RIGHT
            }
        } else {
            if (movingVector.y > 0 && Math.abs(movingVector.x) < Math.abs(movingVector.y)) {
                ROW_TOP_TO_BOTTOM
            } else if (movingVector.y < 0 && Math.abs(movingVector.x) < Math.abs(movingVector.y)) {
                ROW_BOTTOM_TO_TOP
            } else {
                ROW_RIGHT_TO_LEFT
            }
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(currentMoveBitmap, x.toFloat(), y.toFloat(), null)
        lastDrawNanoTime = System.nanoTime()
    }
}*/
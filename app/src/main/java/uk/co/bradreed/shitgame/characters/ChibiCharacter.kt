package uk.co.bradreed.shitgame.characters

import android.graphics.Bitmap
import android.graphics.Canvas
import uk.co.bradreed.shitgame.GameObject
import uk.co.bradreed.shitgame.GameObject.Direction.*
import java.lang.Math.pow
import kotlin.math.sqrt

data class Vector(var x: Int, var y: Int)

class ChibiCharacter(private val gameSurface: GameSurface, image: Bitmap, x: Int, y, Int) : GameObject(image, 4, 3, x, y) {
    companion object {
        const val VELOCITY = 0.1f
    }

    private var rowUsing = ROW_LEFT_TO_RIGHT
    private var colUsing = 0

    private var leftToRights = Array<Bitmap>(colCount) { i -> this.createSubImageAt(ROW_LEFT_TO_RIGHT.value, i) }
    private var rightToLefts = Array<Bitmap>(colCount) { i -> this.createSubImageAt(ROW_RIGHT_TO_LEFT.value, i) }
    private var topToBottoms = Array<Bitmap>(colCount) { i -> this.createSubImageAt(ROW_TOP_TO_BOTTOM.value, i) }
    private var bottomToTops = Array<Bitmap>(colCount) { i -> this.createSubImageAt(ROW_BOTTOM_TO_TOP.value, i) }

    var movingVector: Vector = Vector(10, 5)

    private var lastDrawNanoTime = -1L

    val moveBitmaps: Array<Bitmap>
        get() = when (rowUsing) {
            ROW_BOTTOM_TO_TOP -> bottomToTops
            ROW_LEFT_TO_RIGHT -> leftToRights
            ROW_RIGHT_TO_LEFT -> rightToLefts
            ROW_TOP_TO_BOTTOM -> topToBottoms
        }

    val currentMoveBitmap: Bitmap
        get() = moveBitmaps[colUsing]

    fun update() {
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
        x = (distance * movingVector.x / movingVectorLength).toInt()
        y = (distance * movingVector.x / movingVectorLength).toInt()

        // When the game's character touches the edge of the screen, then change direction
        if (x < 0) {
            x = 0
            movingVector.x = -movingVector.x
        } else if (x > gameSurface.width - width) {
            x = gameSurface.width - width
            movingVector.x = -movingVector.x
        }
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(currentMoveBitmap, x.toFloat(), y.toFloat(), null)
        lastDrawNanoTime = System.nanoTime()
    }
}
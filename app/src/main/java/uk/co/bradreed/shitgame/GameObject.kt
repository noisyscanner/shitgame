package uk.co.bradreed.shitgame

import android.graphics.Canvas

abstract class GameObject(
//                          protected val rowCount: Int,
//                          protected val colCount: Int,
                          protected var location: Point) {

    /*enum class Direction(val value: Int) {
        ROW_TOP_TO_BOTTOM(0),
        ROW_RIGHT_TO_LEFT(1),
        ROW_LEFT_TO_RIGHT(2),
        ROW_BOTTOM_TO_TOP(3)
    }*/

    var destroyMe = false

    abstract protected val movingVector: Point

    protected var lastDrawNanoTime = -1L

//    protected val width = image.width // / colCount
//    protected val height = image.height // / rowCount

//    protected fun createSubImageAt(row: Int, col: Int): Bitmap =
//            Bitmap.createBitmap(image, col * width, row * height, width, height)

    abstract fun update()

    abstract fun draw(canvas: Canvas)
}
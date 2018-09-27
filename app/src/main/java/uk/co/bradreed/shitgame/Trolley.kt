package uk.co.bradreed.shitgame

import android.graphics.Bitmap
import android.graphics.Canvas
import uk.co.bradreed.shitgame.structs.Point

class Trolley(private val bitmap: Bitmap, var location: Point) : GameObject {

    companion object {
        const val DRAWABLE = R.drawable.trolley
    }

    override var destroyMe = false

    override fun update() {}

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, location.x.toFloat(), location.y.toFloat(), null)
    }

    fun moveTo(x: Int) {
        location = Point(x, location.y)
    }
}
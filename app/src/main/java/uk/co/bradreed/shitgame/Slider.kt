package uk.co.bradreed.shitgame

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class Slider(private val gameSurface: GameSurface): GameObject {

    companion object {
        private const val HEIGHT = 100
    }

    val rect
        get() = Rect(
                0,
                gameSurface.height - HEIGHT,
                gameSurface.width,
                gameSurface.height
        )

    override fun draw(canvas: Canvas) {
        val paint = Paint()

        paint.color = Color.BLUE

        canvas.drawRect(rect, paint)
    }
}

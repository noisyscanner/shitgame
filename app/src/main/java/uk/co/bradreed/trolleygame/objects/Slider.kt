package uk.co.bradreed.trolleygame.objects

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import uk.co.bradreed.trolleygame.GameObject
import uk.co.bradreed.trolleygame.R
import uk.co.bradreed.trolleygame.structs.Size

class Slider(private val context: Context, private val gameSurfaceSize: Size) : GameObject {

    companion object {
        private const val MAX_HEIGHT = 150
    }

    private val height get() = Math.min(MAX_HEIGHT, gameSurfaceSize.height / 10)

    val rect
        get() = Rect(
                0,
                gameSurfaceSize.height - height,
                gameSurfaceSize.width,
                gameSurfaceSize.height
        )

    override fun draw(canvas: Canvas) {
        val paint = Paint().apply {
            color = context.resources.getColor(R.color.sainsOrange)
        }

        canvas.drawRect(rect, paint)
    }
}

package uk.co.bradreed.trolleygame.objects

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.content.res.ResourcesCompat
import uk.co.bradreed.trolleygame.GameObject
import uk.co.bradreed.trolleygame.GameSurface
import uk.co.bradreed.trolleygame.R

class Slider(private val gameSurface: GameSurface): GameObject {

    companion object {
        private const val MAX_HEIGHT = 150
    }

    private val height get() = Math.min(MAX_HEIGHT, gameSurface.height / 10)

    val rect
        get() = Rect(
                0,
                gameSurface.height - height,
                gameSurface.width,
                gameSurface.height
        )

    override fun draw(canvas: Canvas) {
        val paint = Paint().apply {
            color = ResourcesCompat.getColor(gameSurface.resources, R.color.sainsOrange, null)
        }

        canvas.drawRect(rect, paint)
    }
}

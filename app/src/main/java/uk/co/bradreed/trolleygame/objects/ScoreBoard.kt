package uk.co.bradreed.trolleygame.objects

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import uk.co.bradreed.trolleygame.GameObject
import uk.co.bradreed.trolleygame.R
import uk.co.bradreed.trolleygame.structs.Point
import uk.co.bradreed.trolleygame.structs.Score

class ScoreBoard(private val context: Context, private val location: Point): GameObject {

    var score = Score(caught = 0, dropped = 0)

    private val fontSize = 50f
    private val basePaint = Paint().apply {
        textSize = fontSize
        isFakeBoldText = true
    }

    override fun draw(canvas: Canvas) {
        drawCaught(canvas)
        drawDropped(canvas)
    }

    private fun drawCaught(canvas: Canvas) {
        val greenPaint = Paint(basePaint).apply {
            color = context.resources.getColor(R.color.green)
        }

        canvas.drawText("✅ ${score.caught}", location.x.toFloat(), location.y.toFloat(), greenPaint)
    }

    private fun drawDropped(canvas: Canvas) {
        val redPaint = Paint(basePaint).apply {
            color = context.resources.getColor(R.color.red)
        }

        canvas.drawText("❌ ${score.dropped}", location.x.toFloat(), location.y.toFloat() + fontSize, redPaint)
    }
}
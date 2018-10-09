package uk.co.bradreed.trolleygame.objects

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.res.ResourcesCompat
import uk.co.bradreed.trolleygame.GameObject
import uk.co.bradreed.trolleygame.R
import uk.co.bradreed.trolleygame.structs.Point
import uk.co.bradreed.trolleygame.structs.Score

class ScoreBoard(private val resources: Resources,
                 private val location: Point,
                 var score: Score): GameObject {

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
            resources.getColor(R.color.green)
        }

        canvas.drawText("✅ ${score.caught}", location.x.toFloat(), location.y.toFloat(), greenPaint)

    }

    private fun drawDropped(canvas: Canvas) {
        val redPaint = Paint(basePaint).apply {
            color = ResourcesCompat.getColor(resources, R.color.red, null)
        }

        canvas.drawText("❌ ${score.dropped}", location.x.toFloat(), location.y.toFloat() + fontSize, redPaint)

    }
}
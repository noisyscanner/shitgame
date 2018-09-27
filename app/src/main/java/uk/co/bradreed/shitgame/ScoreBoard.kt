package uk.co.bradreed.shitgame

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import uk.co.bradreed.shitgame.structs.Point
import uk.co.bradreed.shitgame.structs.Score

class ScoreBoard(private val location: Point): GameObject {
    var score: Score = Score()

    override fun draw(canvas: Canvas) {
        val fontSize = 50f
        val basePaint = Paint().apply {
            textSize = fontSize
            isFakeBoldText = true
        }

        val greenPaint = Paint(basePaint).apply { color = Color.GREEN }
        val redPaint = Paint(basePaint).apply { color = Color.RED }

        canvas.drawText("✅ ${score.caught}", location.x.toFloat(), location.y.toFloat(), greenPaint)
        canvas.drawText("❌ ${score.dropped}", location.x.toFloat(), location.y.toFloat() + fontSize, redPaint)
    }
}
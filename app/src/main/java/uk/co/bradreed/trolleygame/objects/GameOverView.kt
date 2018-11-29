package uk.co.bradreed.trolleygame.objects

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import uk.co.bradreed.trolleygame.GameObject
import uk.co.bradreed.trolleygame.R
import uk.co.bradreed.trolleygame.structs.Score
import uk.co.bradreed.trolleygame.structs.Size

class GameOverView(private val context: Context, private val gameSurfaceSize: Size) : GameObject {

    var score: Score? = null

    companion object {
        private const val TEXT_HEIGHT = 75f
    }

    private val paint = Paint().apply {
        textSize = TEXT_HEIGHT
        color = context.resources.getColor(R.color.sainsOrange)
    }

    fun show(score: Score) {
        this.score = score
    }

    override fun draw(canvas: Canvas) {
        score ?: return

        drawTitle(canvas)
        drawScore(canvas)
    }

    private fun drawTitle(canvas: Canvas) {
        val text = "GAME OVER"

        drawCenteredText(canvas, text)
    }

    private fun drawScore(canvas: Canvas) {
        val text = "You scored ${score!!.caught}${if(score!!.caught == 0) " :(" else "!"}"

        drawCenteredText(canvas, text, line = 1)
    }

    private fun drawCenteredText(canvas: Canvas, text: String, line: Int = 0) {
        val textWidth = paint.measureText(text)

        canvas.drawText(
                text,
                gameSurfaceSize.width / 2 - textWidth / 2,
                gameSurfaceSize.height / 2f + line * TEXT_HEIGHT,
                paint
        )
    }
}
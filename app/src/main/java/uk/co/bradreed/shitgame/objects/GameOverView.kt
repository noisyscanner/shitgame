package uk.co.bradreed.shitgame.objects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import uk.co.bradreed.shitgame.GameObject
import uk.co.bradreed.shitgame.GameSurface
import uk.co.bradreed.shitgame.structs.Score

class GameOverView(private val gameSurface: GameSurface) : GameObject {

    private var score: Score? = null

    companion object {
        private const val TEXT_HEIGHT = 50f
        private val paint = Paint().apply {
            textSize = TEXT_HEIGHT
            color = Color.BLACK
        }
    }

    fun show(score: Score) {
        this.score = score
    }

    fun hide() {
        score = null
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
        val text = "You scored ${score!!.caught}!"

        drawCenteredText(canvas, text, line = 1)
    }

    private fun drawCenteredText(canvas: Canvas, text: String, line: Int = 0) {
        val textWidth = paint.measureText(text)

        canvas.drawText(
                text,
                gameSurface.width / 2 - textWidth / 2,
                gameSurface.height / 2f + line * TEXT_HEIGHT,
                paint
        )
    }
}
package uk.co.bradreed.trolleygame.objects

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import uk.co.bradreed.trolleygame.R
import uk.co.bradreed.trolleygame.structs.Point
import uk.co.bradreed.trolleygame.structs.Score

@RunWith(RobolectricTestRunner::class)
class ScoreBoardTest {

    private val fontSize = 50f

    private lateinit var mockResources: Resources
    private lateinit var mockCanvas: Canvas

    private val location = Point(5, 5)
    private val score = Score(caught = 5, dropped = 5)
    private lateinit var scoreBoard: ScoreBoard

    @Before
    fun setUp() {
        mockCanvas = mock()
        mockResources = mock()

        val mockContext = mock<Context>().apply {
            whenever(resources).thenReturn(mockResources)
        }

        scoreBoard = ScoreBoard(mockContext, location)
        scoreBoard.score = score
    }

    @Test
    fun draw_shouldDrawCaughtInGreen() {
        val fakeGreenColor = Color.GREEN
        whenever(mockResources.getColor(R.color.green)).thenReturn(fakeGreenColor)

        scoreBoard.draw(mockCanvas)

        verify(mockCanvas).drawText(
                eq("✅ ${score.caught}"),
                eq(location.x.toFloat()),
                eq(location.y.toFloat()),
                argForWhich { color == fakeGreenColor }
        )
    }

    @Test
    fun draw_shouldDrawDroppedInRed() {
        val fakeRedColor = Color.RED
        whenever(mockResources.getColor(R.color.red)).thenReturn(fakeRedColor)

        scoreBoard.draw(mockCanvas)

        verify(mockCanvas).drawText(
                eq("❌ ${score.dropped}"),
                eq(location.x.toFloat()),
                eq(location.y.toFloat() + fontSize),
                argForWhich { color == fakeRedColor }
        )
    }
}
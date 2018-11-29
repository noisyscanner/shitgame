package uk.co.bradreed.trolleygame.objects

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import uk.co.bradreed.trolleygame.GameSurface
import uk.co.bradreed.trolleygame.R
import uk.co.bradreed.trolleygame.structs.Score
import uk.co.bradreed.trolleygame.structs.Size

@RunWith(RobolectricTestRunner::class)
class GameOverViewTest {

    companion object {
        private const val TEXT_HEIGHT = 75f
        private const val SURFACE_WIDTH = 300
        private const val SURFACE_HEIGHT = 500
        private const val FAKE_SAINSBURYS_ORANGE_COLOR = 0x553214
    }

    private val expectedPaint = Paint().apply {
        textSize = TEXT_HEIGHT
        color = FAKE_SAINSBURYS_ORANGE_COLOR
    }

    private lateinit var mockResources: Resources

    private lateinit var gameOverView: GameOverView

    private lateinit var mockCanvas: Canvas

    @Before
    fun setUp() {
        mockResources = mock()
        whenever(mockResources.getColor(R.color.sainsOrange)).thenReturn(FAKE_SAINSBURYS_ORANGE_COLOR)

        val mockContext = mock<Context>().apply {
            whenever(resources).thenReturn(mockResources)
        }

        gameOverView = GameOverView(mockContext, Size(SURFACE_WIDTH, SURFACE_HEIGHT))

        mockCanvas = mock()
    }

    @Test
    fun draw_shouldNotDrawAnythingIfScoreIsNull() {
        gameOverView.draw(mockCanvas)

        verifyZeroInteractions(mockCanvas)
    }

    @Test
    fun draw_shouldDrawGameOverTitleInCentre() {
        gameOverView.show(Score(caught = 5, dropped = 3))
        gameOverView.draw(mockCanvas)

        val expectedTitle = "GAME OVER"
        val textWidth = expectedPaint.measureText(expectedTitle)
        val expectedX = SURFACE_WIDTH / 2 - textWidth / 2
        val expectedY = SURFACE_HEIGHT / 2f

        verify(mockCanvas).drawText(
                eq(expectedTitle),
                eq(expectedX),
                eq(expectedY),
                argForWhich {
                    textSize == TEXT_HEIGHT &&
                        color == FAKE_SAINSBURYS_ORANGE_COLOR
                }
        )
    }

    @Test
    fun draw_shouldDrawScoreUnderTitle_withSadFace_whenScoreIsZero() {
        val score = Score(caught = 0, dropped = 3)
        gameOverView.show(score)
        gameOverView.draw(mockCanvas)

        val expectedTitle = "You scored 0 :("
        val textWidth = expectedPaint.measureText(expectedTitle)
        val expectedX = SURFACE_WIDTH / 2 - textWidth / 2
        val expectedY = SURFACE_HEIGHT / 2f + TEXT_HEIGHT

        verify(mockCanvas).drawText(
                any(),
                eq(expectedX),
                eq(expectedY),
                argForWhich {
                    textSize == TEXT_HEIGHT &&
                            color == FAKE_SAINSBURYS_ORANGE_COLOR
                }
        )
    }

    @Test
    fun draw_shouldDrawScoreUnderTitle_withExclamationMark_whenScoreIsGreaterThanZero() {
        val score = Score(caught = 5, dropped = 3)
        gameOverView.show(score)
        gameOverView.draw(mockCanvas)

        val expectedTitle = "You scored 5!"
        val textWidth = expectedPaint.measureText(expectedTitle)
        val expectedX = SURFACE_WIDTH / 2 - textWidth / 2
        val expectedY = SURFACE_HEIGHT / 2f + TEXT_HEIGHT

        verify(mockCanvas).drawText(
                any(),
                eq(expectedX),
                eq(expectedY),
                argForWhich {
                    textSize == TEXT_HEIGHT &&
                            color == FAKE_SAINSBURYS_ORANGE_COLOR
                }
        )
    }

}
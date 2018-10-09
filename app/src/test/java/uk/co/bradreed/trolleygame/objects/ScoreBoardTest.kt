package uk.co.bradreed.trolleygame.objects

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import uk.co.bradreed.trolleygame.R
import uk.co.bradreed.trolleygame.structs.Point
import uk.co.bradreed.trolleygame.structs.Score

@RunWith(MockitoJUnitRunner::class)
class ScoreBoardTest {

    private lateinit var mockResources: Resources

    private val location = Point(5, 5)

    private lateinit var mockCanvas: Canvas

    private lateinit var scoreBoard: ScoreBoard

    @Before
    fun setUp() {
        mockCanvas = mock()
        mockResources = mock()
    }

    @Test
    fun draw_shouldDrawCaughtInGreen() {
        val score = Score(caught = 5, dropped = 0)
        scoreBoard = ScoreBoard(mockResources, location, score)

        val paintCaptor = argumentCaptor<Paint>()

        verify(mockCanvas).drawText(
                "✅ ${score.caught}",
                location.x.toFloat(),
                location.y.toFloat(),
                paintCaptor.capture()
        )

        val color = 0xff4411

        whenever(mockResources.getColor(R.color.green)).thenReturn(color)

        assertEquals(color, paintCaptor.firstValue.color)
    }
}
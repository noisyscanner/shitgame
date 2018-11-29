package uk.co.bradreed.trolleygame.objects

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Rect
import com.nhaarman.mockitokotlin2.argForWhich
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import uk.co.bradreed.trolleygame.R
import uk.co.bradreed.trolleygame.structs.Size

@RunWith(RobolectricTestRunner::class)
class SliderTest {

    companion object {
        private const val MAX_HEIGHT = 150
        private const val SURFACE_HEIGHT = 500
        private const val SURFACE_WIDTH = 300
        private const val FAKE_SAINSBURYS_ORANGE_COLOR = 0xff4455
    }

    @Test
    fun draw_shouldDrawOrangeSliderAtBottomOfScreen() {
        val mockResources = mock<Resources>()
        whenever(mockResources.getColor(R.color.sainsOrange)).thenReturn(FAKE_SAINSBURYS_ORANGE_COLOR)

        val mockContext = mock<Context>().apply {
            whenever(resources).thenReturn(mockResources)
        }

        val slider = Slider(mockContext, Size(SURFACE_WIDTH, SURFACE_HEIGHT))

        val mockCanvas = mock<Canvas>()
        slider.draw(mockCanvas)

        val expectedHeight = Math.min(MAX_HEIGHT, SURFACE_HEIGHT / 10)

        verify(mockCanvas).drawRect(
                argForWhich<Rect> {
                    left == 0 &&
                            top == SURFACE_HEIGHT - expectedHeight &&
                            right == SURFACE_WIDTH &&
                            bottom == SURFACE_HEIGHT
                },
                argForWhich {
                    color == FAKE_SAINSBURYS_ORANGE_COLOR
                }
        )
    }

}
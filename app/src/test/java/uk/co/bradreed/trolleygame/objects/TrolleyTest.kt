package uk.co.bradreed.trolleygame.objects

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import uk.co.bradreed.trolleygame.structs.Point

@RunWith(RobolectricTestRunner::class)
class TrolleyTest {

    private lateinit var rightFacingBitmap: Bitmap
    private val location = Point(x = 0, y = 0)

    private lateinit var trolley: Trolley

    @Before
    fun setUp() {
        rightFacingBitmap = BitmapFactory
                .decodeResource(RuntimeEnvironment.application.resources, Trolley.DRAWABLE)

        trolley = Trolley(rightFacingBitmap, location)
    }

    @Test
    fun draw_shouldDrawRightFacingBitmap_whenDirectionIsRight() {
        val newX = location.x + 5
        trolley.moveTo(newX)

        val expectedNewLocation = location.copy(newX)

        val mockCanvas = mock<Canvas>()
        trolley.draw(mockCanvas)

        verify(mockCanvas).drawBitmap(
                rightFacingBitmap,
                expectedNewLocation.x.toFloat(),
                expectedNewLocation.y.toFloat(),
                null
        )
    }

    // TODO:
//    @Test
//    fun draw_shouldDrawLeftFacingBitmap_whenDirectionIsRight() {
//        val newX = location.x - 5
//        trolley.moveTo(newX)
//
//        val expectedNewLocation = location.copy(newX)
//
//        val mockCanvas = mock<Canvas>()
//        trolley.draw(mockCanvas)
//
//        verify(mockCanvas).drawBitmap(
//                not(eq(rightFacingBitmap)),
//                eq(expectedNewLocation.x.toFloat()),
//                eq(expectedNewLocation.y.toFloat()),
//                isNotNull()
//        )
//    }

}
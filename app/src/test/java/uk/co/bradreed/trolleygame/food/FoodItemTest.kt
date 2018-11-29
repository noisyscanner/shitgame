package uk.co.bradreed.trolleygame.food

import android.graphics.Bitmap
import android.graphics.Canvas
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import uk.co.bradreed.trolleygame.structs.Point
import uk.co.bradreed.trolleygame.structs.Vector

@RunWith(RobolectricTestRunner::class)
class FoodItemTest {

    private var fakeClock = { System.nanoTime() }

    inner class TestFoodItem(bitmap: Bitmap, location: Point) : FoodItem(bitmap, location) {
        override val value = 1
        override fun getTimeNow() = fakeClock()

        val itemVelocity get() = velocity
        val itemMovingVector get() = movingVector
        var itemLastDrawNanoTime
            get() = lastDrawNanoTime
            set(value) { lastDrawNanoTime = value }
    }

    private lateinit var mockBitmap: Bitmap
    private val location = Point(x = 0, y = 0)

    private lateinit var foodItem: TestFoodItem
    private lateinit var mockCanvas: Canvas

    @Before
    fun setUp() {
        mockBitmap = mock()
        mockCanvas = mock()

        foodItem = TestFoodItem(mockBitmap, location)
    }

    @Test
    fun update_updatesLastDrawNanoTime_ifItIsMinus1() {
        val timeNow = 12345L
        fakeClock = { timeNow }

        foodItem.update()

        assertEquals(timeNow, foodItem.itemLastDrawNanoTime)
    }

    @Test
    fun update_updatesLocation() {
        val timeJustNow = 12345000000L
        foodItem.itemLastDrawNanoTime = timeJustNow

        val timeNow = 12345001000L
        fakeClock = { timeNow }

        foodItem.update()

        val deltaTime = (timeNow - timeJustNow) / 1000000

        val distanceTravelled = foodItem.itemVelocity * deltaTime

        val nextLocation = location + Vector(
                dx = (distanceTravelled * foodItem.itemMovingVector.dx / foodItem.itemMovingVector.length).toInt(),
                dy = (distanceTravelled * foodItem.itemMovingVector.dy / foodItem.itemMovingVector.length).toInt()
        )

        assertEquals(nextLocation, foodItem.location)
    }

    @Test
    fun draw_doesNotDraw_ifDestroyMeIsTrue() {
        foodItem.destroyMe = true

        foodItem.draw(mockCanvas)

        verifyZeroInteractions(mockCanvas)
    }

    @Test
    fun draw_drawsTheFoodItem() {
        foodItem.draw(mockCanvas)

        verify(mockCanvas).drawBitmap(
                mockBitmap,
                location.x.toFloat(),
                location.y.toFloat(),
                null
        )
    }

    @Test
    fun draw_updatesLastDrawNanoTime() {
        val timeNow = 12345L
        fakeClock = { timeNow }

        foodItem.draw(mockCanvas)

        assertEquals(timeNow, foodItem.itemLastDrawNanoTime)
    }

}
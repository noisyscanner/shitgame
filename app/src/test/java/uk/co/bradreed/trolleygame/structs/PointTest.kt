package uk.co.bradreed.trolleygame.structs

import junit.framework.Assert.assertEquals
import org.junit.Test

class PointTest {
    @Test
    fun plusVector() {
        val point = Point(x = 5, y = 3)
        val vector = Vector(dx = 10, dy = 5)

        val expectedPoint = Point(x = 15, y = 8)
        assertEquals(expectedPoint, point + vector)
    }
}
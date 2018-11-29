package uk.co.bradreed.trolleygame.structs

import org.junit.Assert.*
import org.junit.Test

class VectorTest {

    @Test
    fun length_shouldCalculateHypotenuse() {
        val vector = Vector(dx = 3, dy = 4)

        assertEquals(5.0, vector.length, 0.0)
    }
}
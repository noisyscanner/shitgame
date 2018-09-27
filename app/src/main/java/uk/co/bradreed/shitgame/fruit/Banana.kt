package uk.co.bradreed.shitgame.fruit

import android.graphics.Bitmap
import uk.co.bradreed.shitgame.GameSurface
import uk.co.bradreed.shitgame.R
import uk.co.bradreed.shitgame.random
import uk.co.bradreed.shitgame.structs.Point
import uk.co.bradreed.shitgame.structs.Vector

class Banana(gameSurface: GameSurface, bitmap: Bitmap, initialLocation: Point) :
        Fruit(gameSurface, bitmap, initialLocation) {

    companion object {
        const val DRAWABLE = R.drawable.banana

        private val movingVector = Vector(0, 1)
    }

    override val velocity = (0.5..0.75).random()

    override fun getNextLocation(distanceTravelled: Double) = location + Point(
            x = (distanceTravelled * movingVector.x / movingVector.length).toInt(),
            y = (distanceTravelled * movingVector.y / movingVector.length).toInt()
    )
}
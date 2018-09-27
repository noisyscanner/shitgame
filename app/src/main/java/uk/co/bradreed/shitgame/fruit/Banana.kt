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
    }

    override val velocity = (0.5..0.75).random()
    override val movingVector = Vector(0, 1)
}
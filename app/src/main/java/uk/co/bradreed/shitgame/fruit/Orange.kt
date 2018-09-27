package uk.co.bradreed.shitgame.fruit

import android.graphics.Bitmap
import uk.co.bradreed.shitgame.GameSurface
import uk.co.bradreed.shitgame.R
import uk.co.bradreed.shitgame.Sprite
import uk.co.bradreed.shitgame.random
import uk.co.bradreed.shitgame.structs.Point
import uk.co.bradreed.shitgame.structs.Vector

@Sprite(R.drawable.orange)
class Orange(gameSurface: GameSurface, bitmap: Bitmap, initialLocation: Point) :
        Fruit(gameSurface, bitmap, initialLocation) {

    override val velocity = (0.5..0.75).random()
    override val movingVector = Vector(0, 1)
}
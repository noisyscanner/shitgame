package uk.co.bradreed.shitgame.food

import android.graphics.Bitmap
import uk.co.bradreed.shitgame.GameSurface
import uk.co.bradreed.shitgame.R
import uk.co.bradreed.shitgame.Sprite
import uk.co.bradreed.shitgame.structs.Point

@Sprite(layout = R.drawable.strawberry, probability = 0.4)
class Strawberry(gameSurface: GameSurface, bitmap: Bitmap, initialLocation: Point) :
        FoodItem(gameSurface, bitmap, initialLocation) {

    override val value = 1

}
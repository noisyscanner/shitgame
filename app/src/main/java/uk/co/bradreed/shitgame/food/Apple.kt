package uk.co.bradreed.shitgame.food

import android.graphics.Bitmap
import uk.co.bradreed.shitgame.GameSurface
import uk.co.bradreed.shitgame.R
import uk.co.bradreed.shitgame.Sprite
import uk.co.bradreed.shitgame.structs.Point

@Sprite(layout = R.drawable.apple, probability = 0.8)
class Apple(gameSurface: GameSurface, bitmap: Bitmap, initialLocation: Point) :
        FoodItem(gameSurface, bitmap, initialLocation) {

    override val value = 1

}
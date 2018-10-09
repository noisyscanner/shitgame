package uk.co.bradreed.trolleygame.food

import android.graphics.Bitmap
import uk.co.bradreed.trolleygame.GameSurface
import uk.co.bradreed.trolleygame.R
import uk.co.bradreed.trolleygame.Sprite
import uk.co.bradreed.trolleygame.structs.Point

@Sprite(layout = R.drawable.sausage, probability = 0.1)
class Sausage(gameSurface: GameSurface, bitmap: Bitmap, initialLocation: Point) :
        FoodItem(gameSurface, bitmap, initialLocation) {

    override val value = 1

}
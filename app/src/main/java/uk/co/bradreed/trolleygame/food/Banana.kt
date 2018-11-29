package uk.co.bradreed.trolleygame.food

import android.graphics.Bitmap
import uk.co.bradreed.trolleygame.GameSurface
import uk.co.bradreed.trolleygame.R
import uk.co.bradreed.trolleygame.Sprite
import uk.co.bradreed.trolleygame.structs.Point

@Sprite(layout = R.drawable.banana, probability = 0.7)
class Banana(bitmap: Bitmap, initialLocation: Point) :
        FoodItem(bitmap, initialLocation) {

    override val value = 1

}
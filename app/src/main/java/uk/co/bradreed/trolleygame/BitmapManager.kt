package uk.co.bradreed.trolleygame

import android.content.Context
import android.graphics.BitmapFactory
import uk.co.bradreed.trolleygame.objects.Trolley
import kotlin.reflect.full.findAnnotation

class BitmapManager(private val context: Context, private val surfaceWidth: Int) {

    val trolleyBitmap by lazy {
        BitmapFactory
                .decodeResource(context.resources, Trolley.DRAWABLE)
                .scaleToWidth(surfaceWidth / 6)
    }

    fun getFoodBitmaps(foodTypes: Collection<FoodType>) =
            foodTypes.map { fruitType ->
                fruitType to fruitType.findAnnotation<Sprite>()?.layout?.let { resId ->
                    BitmapFactory
                            .decodeResource(context.resources, resId)
                            .scaleToWidth(surfaceWidth / 12)
                }
            }.toMap()

}

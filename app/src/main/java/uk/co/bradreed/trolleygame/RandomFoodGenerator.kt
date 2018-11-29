package uk.co.bradreed.trolleygame

import kotlin.reflect.full.findAnnotation

class RandomFoodGenerator(foodTypes: List<FoodType>) {

    private val distribution = foodTypes
            .mapNotNull { foodType ->
                foodType.findAnnotation<Sprite>()?.probability?.let { p -> foodType to p }
            }
            .toMap()

    private val ratio = 1f / distribution
            .toList()
            .fold(0.0) { sum, (_, p) -> sum + p }

    val randomFood: FoodType?
        get() {
            val rand = Math.random()
            var tempDist = 0.0

            return distribution.entries.find { (_, dist) ->
                tempDist += dist
                rand / ratio <= tempDist
            }?.key
        }
}

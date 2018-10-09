package uk.co.bradreed.trolleygame

import kotlin.reflect.full.findAnnotation

class RandomFoodGenerator(foodTypes: List<FoodType>) {

    private val distribution: Map<FoodType, Double> = foodTypes
            .mapNotNull { foodType ->
                foodType.findAnnotation<Sprite>()?.probability?.let { p -> foodType to p }
            }
            .toMap()

    private val distSum = distribution
            .toList()
            .fold(0.0) { sum, (_, p) -> sum + p }

    val randomFood: FoodType?
        get() {
            val rand = Math.random()
            val ratio = 1f / distSum
            var tempDist = 0.0

            distribution.forEach { (foodType, dist) ->
                tempDist += dist
                if (rand / ratio <= tempDist) {
                    return foodType
                }
            }

            return null
        }
}

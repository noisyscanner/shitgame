package uk.co.bradreed.trolleygame

import uk.co.bradreed.trolleygame.food.*
import uk.co.bradreed.trolleygame.structs.Score

class GameManager(private val gameSurface: GameSurface) {
    val bitmapManager by lazy { BitmapManager(gameSurface.context, gameSurface.width) }
    val foodSpawner by lazy { FoodSpawner(gameSurface.width, bitmapManager) }

    var gameOver = false
        set(isOver) {
            field = isOver
            if (isOver) {
                foodSpawner.end()
            } else {
                foodSpawner.resume()
            }
        }

    private var score = Score()
        set(newValue) {
            field = newValue
            foodSpawner.setSpeedFromScore(score.caught)
            gameSurface.updateScore(score)

            if (score.dropped >= 3) gameOver = true
        }

    fun restartGame() {
        score = Score()
        gameOver = false
    }

    fun update() =
            foodSpawner.fruit.forEach { foodItem ->
                if (foodItem.destroyMe) return@forEach

                if (foodItemIsCaught(foodItem)) {
                    score = score.copy(caught = score.caught + 1)
                    foodItem.destroyMe = true
                    return@forEach
                }

                if (foodItemIsDropped(foodItem)) {
                    score = score.copy(dropped = score.dropped + 1)
                    foodItem.destroyMe = true
                    return@forEach
                }

                foodItem.update()
            }

    private fun foodItemIsCaught(item: FoodItem): Boolean {
        val trolleyWidth = gameSurface.trolley.width
        val trolleyLoc = gameSurface.trolley.location

        return item.location.y >= trolleyLoc.y - item.bitmap.height &&
                item.location.x >= trolleyLoc.x &&
                item.location.x <= trolleyLoc.x + trolleyWidth - item.bitmap.width
    }

    private fun foodItemIsDropped(item: FoodItem) =
            item.location.y >= gameSurface.slider.rect.top - item.bitmap.height
}
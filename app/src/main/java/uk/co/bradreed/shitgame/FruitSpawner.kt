package uk.co.bradreed.trolleygame

import android.graphics.Bitmap
import uk.co.bradreed.trolleygame.food.FoodItem
import uk.co.bradreed.trolleygame.structs.Point
import java.lang.Math.log10
import java.lang.Math.pow
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

typealias FoodType = KClass<out FoodItem>

class FruitSpawner(private val surface: GameSurface, private val bitmaps: Map<FoodType, Bitmap?>) {

    private var thread: FruitSpawnerThread? = null

    private class FruitSpawnerThread(private val spawner: FruitSpawner) : Thread() {
        private var running = false
        private var millisBetween = 1000L

        override fun start() {
            running = true

            super.start()
        }

        fun end() {
            running = false
        }

        override fun run() {
            super.run()

            while (running) {
                spawner.addRandomFruit()
                Thread.sleep(millisBetween)
            }
        }

        fun setSpeedFromScore(score: Int) {
            val sub = (pow(log10(score.toDouble() * 5), 2.0) * 100).toInt()
            if (sub < 1000) {
                millisBetween = 1000L - sub
            }
        }
    }

    private val generator by lazy { RandomFoodGenerator(bitmaps.keys.toList()) }

    var fruit = listOf<FoodItem>()
    private val visibleFruit get() = fruit.filter { !it.destroyMe }


    fun resume() {
        thread = FruitSpawnerThread(this)
        thread?.start()
    }

    fun pause() {
        thread?.end()
    }

    fun end() {
        fruit.forEach { it.destroyMe = true }
        pause()
    }

    private fun addRandomFruit() {
        createFruit()?.let { newFruit ->
            fruit = visibleFruit + newFruit
        }
    }

    private fun createFruit(): FoodItem? {
        return generator.randomFood?.let { foodType ->
            bitmaps[foodType]?.let { bitmap ->
                foodType.primaryConstructor?.call(
                        surface,
                        bitmap,
                        getRandomSpawnPointForBitmap(bitmap)
                )
            }
        }
    }

    private fun getRandomSpawnPointForBitmap(bitmap: Bitmap) = Point(
            x = (0..(surface.width - bitmap.width)).random(),
            y = 0 - bitmap.height
    )

    fun setSpeedFromScore(score: Int) = thread?.setSpeedFromScore(score)
}
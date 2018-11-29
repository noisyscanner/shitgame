package uk.co.bradreed.trolleygame

import android.graphics.Bitmap
import uk.co.bradreed.trolleygame.food.*
import uk.co.bradreed.trolleygame.structs.Point
import java.lang.Math.log10
import java.lang.Math.pow
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

typealias FoodType = KClass<out FoodItem>

class FoodSpawner(private val surfaceWidth: Int, private val bitmapManager: BitmapManager) {

    private val foodTypes = listOf(
            Apple::class,
            Banana::class,
            Bread::class,
            Broccoli::class,
            Cheese::class,
            Orange::class,
            Sausage::class,
            Strawberry::class
    )

    private val bitmaps by lazy { bitmapManager.getFoodBitmaps(foodTypes) }

    private class FruitSpawnerThread(private val spawner: FoodSpawner) : Thread() {
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

    private var thread: FruitSpawnerThread? = null
    private val generator by lazy { RandomFoodGenerator(foodTypes) }

    var fruit = listOf<FoodItem>()
    private val visibleFruit get() = fruit.filter { !it.destroyMe }

    fun resume() {
        thread = FruitSpawnerThread(this)
        thread?.start()
    }

    fun pause() {
        fruit = listOf()
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

    private fun createFruit(): FoodItem? =
        generator.randomFood?.let { foodType ->
            bitmaps[foodType]?.let { bitmap ->
                foodType.primaryConstructor?.call(
                        bitmap,
                        getRandomSpawnPointForBitmap(bitmap)
                )
            }
        }

    private fun getRandomSpawnPointForBitmap(bitmap: Bitmap) = Point(
            x = (0..(surfaceWidth - bitmap.width)).random(),
            y = 0 - bitmap.height
    )

    fun setSpeedFromScore(score: Int) = thread?.setSpeedFromScore(score)
}
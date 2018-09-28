package uk.co.bradreed.shitgame

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import uk.co.bradreed.shitgame.food.*
import uk.co.bradreed.shitgame.structs.Point
import java.lang.Math.log10
import java.lang.Math.pow
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

typealias FoodType = KClass<out FoodItem>

class FruitSpawner(private val surface: GameSurface) : Thread() {
    private val fruitTypes = listOf(
            Apple::class,
            Banana::class,
            Bread::class,
            Broccoli::class,
            Cheese::class,
            Orange::class,
            Sausage::class,
            Strawberry::class
    )
    private val generator by lazy { RandomFoodGenerator(fruitTypes) }

    var fruit = listOf<FoodItem>()
    private val visibleFruit get() = fruit.filter { !it.destroyMe }

    private lateinit var bitmaps: Map<FoodType, Bitmap?>

    private var running = false
    private var millisBetween = 1000L

    override fun run() {
        super.run()

        while (running) {
            createFruit()?.let { newFruit ->
                fruit = visibleFruit + newFruit
                sleep(millisBetween)
            }
        }
    }

    override fun start() {
        bitmaps = loadBitmaps()
        running = true

        super.start()
    }

    fun end() {
        running = false
    }

    private fun createFruit(): FoodItem? {
        return generator.randomFood?.let { foodType ->
            bitmaps[foodType]?.let { bitmap ->
                foodType.constructors.first().call(
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

    fun setSpeedFromScore(score: Int) {
        val sub = (pow(log10(score.toDouble() * 5), 2.0) * 100).toInt()
        if (sub < 1000) {
            millisBetween = 1000L - sub
        }
    }

    private fun loadBitmaps() = fruitTypes.map { fruitType ->
        fruitType to fruitType.findAnnotation<Sprite>()?.layout?.let { resId ->
            BitmapFactory
                    .decodeResource(surface.resources, resId)
                    .scaleToWidth(surface.width / 12)
        }
    }.toMap()
}
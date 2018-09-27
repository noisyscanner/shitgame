package uk.co.bradreed.shitgame

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import uk.co.bradreed.shitgame.fruit.Banana
import uk.co.bradreed.shitgame.fruit.Fruit
import uk.co.bradreed.shitgame.fruit.Orange
import uk.co.bradreed.shitgame.structs.Point
import java.lang.Math.log10
import java.lang.Math.pow
import java.util.*
import kotlin.reflect.KClass

class FruitSpawner(private val surface: GameSurface) : Thread() {
    private val fruitTypes = listOf(Orange::class, Banana::class)

    var fruit = listOf<Fruit>()

    private lateinit var bitmaps: Map<KClass<out Fruit>, Bitmap>

    private val visibleFruit get() = fruit.filter { !it.destroyMe }

    private var running = false

    private var millisBetween = 1000L

    override fun run() {
        super.run()

        while (running) {
            createFruit()?.let { newFruit ->
                fruit = visibleFruit + newFruit
            }

            sleep(millisBetween)
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

    private fun createFruit(): Fruit? {
        val fruitType = fruitTypes[Random().nextInt(fruitTypes.size)]
        bitmaps[fruitType]?.let { bitmap ->
            return when (fruitType) {
                Orange::class -> Orange(surface, bitmap, getRandomSpawnPointForBitmap(bitmap))
                Banana::class -> Banana(surface, bitmap, getRandomSpawnPointForBitmap(bitmap))
                else -> null
            }
        }

        return null
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

    private fun loadBitmaps(): Map<KClass<out Fruit>, Bitmap> = fruitTypes.map { fruitType ->
        val resId = fruitType.java.getDeclaredField("DRAWABLE").get(null) as Int

        fruitType to BitmapFactory
                .decodeResource(surface.resources, resId)
                .scaleToWidth(surface.width / 12)
    }.toMap()
}
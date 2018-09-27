package uk.co.bradreed.shitgame

import android.graphics.Bitmap
import uk.co.bradreed.shitgame.objects.Orange

class FruitSpawner(private val surface: GameSurface) : Thread() {
    var fruit = listOf<GameObject>()
    private val visibleFruit get() = fruit.filter { !it.destroyMe }

    private var running = false

    private var millisBetween = 1000L

    override fun run() {
        super.run()

        while (running) {
            fruit = visibleFruit + createOrange()

            sleep(millisBetween)
        }
    }

    override fun start() {
        super.start()

        running = true
    }

    fun end() {
        running = false
    }

    private fun createOrange(): Orange {
        val spawnAt = getRandomSpawnPointForBitmap(Orange.bitmap)

        return Orange(surface, spawnAt)
    }

    private fun getRandomSpawnPointForBitmap(bitmap: Bitmap) = Point(
            x = (0..(surface.width - bitmap.width)).random(),
            y = 0 - bitmap.height
    )
}
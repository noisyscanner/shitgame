package uk.co.bradreed.shitgame

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.SurfaceHolder
import android.view.SurfaceView
import uk.co.bradreed.shitgame.objects.Orange

class GameSurface(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    private var gameThread: GameThread? = null
    private var fruitSpawner: FruitSpawner? = null

    init {
        isFocusable = true
        holder.addCallback(this)
    }

    fun update() {
        fruitSpawner?.fruit?.forEach { it.update() }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        fruitSpawner?.fruit?.forEach { it.draw(canvas) }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        loadResources()

        gameThread = GameThread(this, holder)
        gameThread?.running = true
        gameThread?.start()

        fruitSpawner = FruitSpawner(this)
        fruitSpawner?.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) { }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                gameThread?.running = false
                fruitSpawner?.end()

                // Parent thread must wait until the end of GameThread.
                gameThread?.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            retry = true
        }
    }

    private fun loadResources() {
        Orange.bitmap = BitmapFactory
                .decodeResource(resources, Orange.DRAWABLE)
                .scaleToWidth(width / 10)
    }
}
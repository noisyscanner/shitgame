package uk.co.bradreed.shitgame

import android.graphics.Canvas
import android.view.SurfaceHolder

class GameThread(private val gameSurface: GameSurface,
                 private val surfaceHolder: SurfaceHolder): Thread() {

    var running = false

    override fun run() {
        var startTime = System.nanoTime()

        while (running) {
            var canvas: Canvas? = null

            try {
                canvas = surfaceHolder.lockCanvas()

                synchronized(canvas) {
                    gameSurface.apply {
                        update()
                        draw(canvas)
                    }
                }
            } catch (_: Exception) {
            }
            finally {
                canvas?.let(surfaceHolder::unlockCanvasAndPost)
            }

            val now = System.nanoTime()

            // Interval to redraw game
            // (Change nanoseconds to milliseconds)
            var waitTime = (now - startTime) / 1000000
            if (waitTime < 10) {
                waitTime = 10 // Millisecond
            }
            print("Wait Time=" + waitTime)

            try {
                sleep(waitTime)
            } catch (e: InterruptedException) {

            }

            startTime = System.nanoTime()
            print(".")
        }
    }

}
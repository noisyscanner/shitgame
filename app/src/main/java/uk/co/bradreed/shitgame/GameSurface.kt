package uk.co.bradreed.shitgame

import android.content.Context
import android.view.SurfaceHolder
import uk.co.bradreed.shitgame.characters.ChibiCharacter
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.SurfaceView

class GameSurface(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    private var gameThread: GameThread? = null

    private var chibi1: ChibiCharacter? = null

    init {
        isFocusable = true
        holder.addCallback(this)
    }

    fun update() {
        this.chibi1?.update()
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        chibi1?.draw(canvas)
    }

    // Implements method of SurfaceHolder.Callback
    override fun surfaceCreated(holder: SurfaceHolder) {
        val chibiBitmap1 = BitmapFactory.decodeResource(this.resources, R.drawable.chibi1)
        this.chibi1 = ChibiCharacter(this, chibiBitmap1, 100, 50)

        gameThread = GameThread(this, holder)
        gameThread?.running = true
        gameThread?.start()
    }

    // Implements method of SurfaceHolder.Callback
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    // Implements method of SurfaceHolder.Callback
    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                gameThread?.running = false

                // Parent thread must wait until the end of GameThread.
                gameThread?.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            retry = true
        }
    }
}
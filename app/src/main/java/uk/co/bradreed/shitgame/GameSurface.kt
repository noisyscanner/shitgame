package uk.co.bradreed.shitgame

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import uk.co.bradreed.shitgame.structs.Point
import uk.co.bradreed.shitgame.structs.Score

class GameSurface(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    private var gameThread: GameThread? = null
    private var fruitSpawner = FruitSpawner(this)

    private val slider = Slider(this)
    private lateinit var scoreBoard: ScoreBoard
    lateinit var trolley: Trolley

    private var score: Score = Score()
        set(newValue) {
            field = newValue
            fruitSpawner.setSpeedFromScore(score.caught)
            scoreBoard.score = score
        }

    init {
        isFocusable = true
        holder.addCallback(this)
    }

    fun update() {
        fruitSpawner.fruit.forEach { it.update() }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        canvas.drawColor(Color.WHITE)

        fruitSpawner.fruit.forEach { it.draw(canvas) }

        scoreBoard.draw(canvas)
        trolley.draw(canvas)
        slider.draw(canvas)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        scoreBoard = ScoreBoard(Point(x = 50, y = 75))
        trolley = loadTrolley()

        gameThread = GameThread(this, holder)
        gameThread?.running = true
        gameThread?.start()

        fruitSpawner.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) { }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                gameThread?.running = false
                fruitSpawner.end()

                // Parent thread must wait until the end of GameThread.
                gameThread?.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            retry = true
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false

        val x = event.x.toInt()
        val y = event.y.toInt()

        return when (event.action) {
            MotionEvent.ACTION_DOWN -> slider.rect.contains(x, y)
            MotionEvent.ACTION_MOVE -> {
                    trolley.moveTo(x)
                    true
                }
            else -> false
        }
    }

    fun onCatchFruit() {
        score = Score(score.caught + 1, score.dropped)
    }

    fun onDropFruit() {
        score = Score(score.caught, score.dropped + 1)
    }

    private fun loadTrolley(): Trolley {
        val trolleyBitmap = BitmapFactory
                .decodeResource(resources, Trolley.DRAWABLE)
                .scaleToWidth(width / 6)

        val startPoint = Point(slider.rect.centerX(), slider.rect.top - trolleyBitmap.height)

        return Trolley(trolleyBitmap, startPoint)
    }
}
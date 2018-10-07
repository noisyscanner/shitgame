package uk.co.bradreed.trolleygame

import android.content.Context
import android.graphics.*
import android.util.DisplayMetrics.DENSITY_DEFAULT
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import uk.co.bradreed.trolleygame.food.*
import uk.co.bradreed.trolleygame.objects.GameOverView
import uk.co.bradreed.trolleygame.objects.ScoreBoard
import uk.co.bradreed.trolleygame.objects.Slider
import uk.co.bradreed.trolleygame.objects.Trolley
import uk.co.bradreed.trolleygame.structs.Point
import uk.co.bradreed.trolleygame.structs.Score
import kotlin.reflect.full.findAnnotation


class GameSurface(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    private var gameThread: GameThread? = null
    private var fruitSpawner: FruitSpawner? = null

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
    private var bitmaps: Map<FoodType, Bitmap?>? = null

    private val gameOverView = GameOverView(this)
    private val slider = Slider(this)

    private lateinit var scoreBoard: ScoreBoard
    lateinit var trolley: Trolley

    private var gameOver = false
        set(isOver) {
            field = isOver
            if (isOver) {
                fruitSpawner?.end()
                gameOverView.show(score)
            } else {
                gameOverView.hide()
                fruitSpawner?.resume()
            }
        }

    private var score: Score = Score()
        set(newValue) {
            field = newValue
            fruitSpawner?.setSpeedFromScore(score.caught)
            scoreBoard.score = score

            if (score.dropped >= 3) gameOver = true
        }

    init {
        isFocusable = true
        holder.addCallback(this)
    }

    fun update() {
        fruitSpawner?.fruit?.forEach { it.update() }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        canvas.drawColor(Color.WHITE)

        fruitSpawner?.fruit?.forEach { it.draw(canvas) }

        scoreBoard.draw(canvas)
        trolley.draw(canvas)
        slider.draw(canvas)
        gameOverView.draw(canvas)
    }

    private fun resume() {
        gameThread = GameThread(this, holder)
    }

    private fun pause() {
        var retry = true
        while (retry) {
            try {
                gameThread?.running = false
                fruitSpawner?.pause()

                // Parent thread must wait until the end of GameThread.
                gameThread?.join()

                gameThread = null

                retry = false
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        scoreBoard = ScoreBoard(this, Point(x = 50, y = 75), score)
        trolley = loadTrolley()

        if (bitmaps == null) {
            bitmaps = loadBitmaps()
        }

        if (fruitSpawner == null) {
            fruitSpawner = FruitSpawner(this, bitmaps!!)
        }

        resume()
        gameThread?.running = true
        gameThread?.start()

        if (!gameOver) fruitSpawner?.resume()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) = pause()

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false

        val x = event.x.toInt()
        val y = event.y.toInt()

        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (gameOver) {
                    restartGame()
                }
                slider.rect.contains(x, y)
            }
            MotionEvent.ACTION_MOVE -> {
                trolley.moveTo(x)
                true
            }
            else -> false
        }
    }

    fun onCatchFruit() {
        score = score.copy(caught = score.caught + 1)
    }

    fun onDropFruit() {
        score = score.copy(dropped = score.dropped + 1)
    }

    private fun restartGame() {
        score = Score()
        gameOver = false
    }

    private fun loadTrolley(): Trolley {
        val trolleyBitmap = BitmapFactory
                .decodeResource(resources, Trolley.DRAWABLE)
                .scaleToWidth(width / 6)

        val backwardsTrolleyBitmap = Bitmap.createBitmap(trolleyBitmap,
                0,
                0,
                trolleyBitmap.width,
                trolleyBitmap.height,
                Matrix().apply { preScale(-1f, 1f) },
                false
        ).apply { density = DENSITY_DEFAULT }

        val startPoint = Point(slider.rect.centerX(), slider.rect.top - trolleyBitmap.height)

        return Trolley(trolleyBitmap, backwardsTrolleyBitmap, startPoint)
    }

    private fun loadBitmaps() =
        fruitTypes.map { fruitType ->
            fruitType to fruitType.findAnnotation<Sprite>()?.layout?.let { resId ->
                BitmapFactory
                        .decodeResource(resources, resId)
                        .scaleToWidth(width / 12)
            }
        }.toMap()
}
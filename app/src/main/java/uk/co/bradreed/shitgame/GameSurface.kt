package uk.co.bradreed.shitgame

import android.content.Context
import android.graphics.*
import android.util.DisplayMetrics.DENSITY_DEFAULT
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import uk.co.bradreed.shitgame.food.*
import uk.co.bradreed.shitgame.objects.GameOverView
import uk.co.bradreed.shitgame.objects.ScoreBoard
import uk.co.bradreed.shitgame.objects.Slider
import uk.co.bradreed.shitgame.objects.Trolley
import uk.co.bradreed.shitgame.structs.Point
import uk.co.bradreed.shitgame.structs.Score
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
    private lateinit var bitmaps: Map<FoodType, Bitmap?>

    private val gameOverView = GameOverView(this)
    private val slider = Slider(this)

    private lateinit var scoreBoard: ScoreBoard
    lateinit var trolley: Trolley

    private var gameOver = false
        set(isOver) {
            field = isOver
            if (isOver) {
                stopGame()
                gameOverView.show(score)
            } else {
                gameOverView.hide()
                startGame()
            }
        }

    private var score: Score = Score()
        set(newValue) {
            field = newValue
            fruitSpawner?.setSpeedFromScore(score.caught)
            scoreBoard.score = score

            if (score.dropped >= 3) gameOver()
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

    override fun surfaceCreated(holder: SurfaceHolder) {
        scoreBoard = ScoreBoard(this, Point(x = 50, y = 75))
        trolley = loadTrolley()

        gameThread = GameThread(this, holder)
        gameThread?.running = true
        gameThread?.start()

        loadBitmaps()
        startGame()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

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

    private fun startGame() {
        fruitSpawner = FruitSpawner(this, bitmaps)
        fruitSpawner?.start()
    }

    private fun restartGame() {
        score = Score()
        gameOver = false
    }

    private fun stopGame() {
        fruitSpawner?.end()
        fruitSpawner = null
    }

    private fun gameOver() {
        gameOver = true
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

    private fun loadBitmaps() {
        bitmaps = fruitTypes.map { fruitType ->
            fruitType to fruitType.findAnnotation<Sprite>()?.layout?.let { resId ->
                BitmapFactory
                        .decodeResource(resources, resId)
                        .scaleToWidth(width / 12)
            }
        }.toMap()
    }
}
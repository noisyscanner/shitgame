package uk.co.bradreed.trolleygame

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import uk.co.bradreed.trolleygame.objects.GameOverView
import uk.co.bradreed.trolleygame.objects.ScoreBoard
import uk.co.bradreed.trolleygame.objects.Slider
import uk.co.bradreed.trolleygame.objects.Trolley
import uk.co.bradreed.trolleygame.structs.Point
import uk.co.bradreed.trolleygame.structs.Score
import uk.co.bradreed.trolleygame.structs.Size

open class GameSurface(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    private val gameManager = GameManager(this)
    private var gameThread: GameThread? = null

    private lateinit var gameOverView: GameOverView
    private lateinit var scoreBoard: ScoreBoard
    lateinit var slider: Slider
    lateinit var trolley: Trolley

    init {
        isFocusable = true
        holder.addCallback(this)
    }

    fun update() = gameManager.update()

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        canvas.drawColor(Color.WHITE)

        gameManager.foodSpawner.fruit.forEach { it.draw(canvas) }

        slider.draw(canvas)

        if (gameManager.gameOver) {
            gameOverView.draw(canvas)
        } else {
            scoreBoard.draw(canvas)
            trolley.draw(canvas)
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        val size = Size.ofSurface(this)

        gameOverView = GameOverView(context, size)
        slider = Slider(context, size)
        scoreBoard = ScoreBoard(context, Point(x = 50, y = 75))
        trolley = loadTrolley()

        start()

        if (!gameManager.gameOver) gameManager.foodSpawner.resume()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) = pause()

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false

        val x = event.x.toInt()
        val y = event.y.toInt()

        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (gameManager.gameOver) {
                    gameManager.restartGame()
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

    fun updateScore(score: Score) {
        scoreBoard.score = score
        gameOverView.score = score
    }

    private fun loadTrolley() = gameManager.bitmapManager.trolleyBitmap.let { bitmap ->
        val startPoint = Point(slider.rect.centerX(), slider.rect.top - bitmap.height)
        Trolley(bitmap, startPoint)
    }

    private fun start() {
        gameThread = GameThread(this, holder)
        gameThread?.running = true
        gameThread?.start()
    }

    private fun pause() {
        var retry = true
        while (retry) {
            try {
                gameThread?.running = false
                gameManager.foodSpawner.pause()

                // Parent thread must wait until the end of GameThread.
                gameThread?.join()

                gameThread = null

                retry = false
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}
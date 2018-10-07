package uk.co.bradreed.shitgame

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window.FEATURE_NO_TITLE
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN

class MainActivity : Activity() {

    private var gameSurface: GameSurface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN)

        requestWindowFeature(FEATURE_NO_TITLE)

        gameSurface = GameSurface(this)

        setContentView(gameSurface)
    }
}


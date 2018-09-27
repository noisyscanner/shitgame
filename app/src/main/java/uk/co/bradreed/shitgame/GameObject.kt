package uk.co.bradreed.shitgame

import android.graphics.Canvas

interface GameObject {
    var destroyMe: Boolean

    fun update()

    fun draw(canvas: Canvas)
}
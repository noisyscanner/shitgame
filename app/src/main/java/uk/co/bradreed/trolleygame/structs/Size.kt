package uk.co.bradreed.trolleygame.structs

import android.view.SurfaceView

data class Size(val width: Int, val height: Int) {
    companion object {
        fun ofSurface(surfaceView: SurfaceView) = Size(surfaceView.width, surfaceView.height)
    }
}
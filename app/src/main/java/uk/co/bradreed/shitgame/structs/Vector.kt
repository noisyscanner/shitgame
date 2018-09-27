package uk.co.bradreed.shitgame.structs

import java.lang.Math.pow
import java.lang.Math.sqrt

data class Vector(val dx: Int, val dy: Int) {
    val length get() = sqrt(pow(dx.toDouble(), 2.0) + pow(dy.toDouble(), 2.0))
}
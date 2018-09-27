package uk.co.bradreed.shitgame.structs

import java.lang.Math.pow
import java.lang.Math.sqrt

data class Vector(val x: Int, val y: Int) {
    val length get() = sqrt(pow(x.toDouble(), 2.0) + pow(y.toDouble(), 2.0))
}
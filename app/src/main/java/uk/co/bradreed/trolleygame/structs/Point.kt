package uk.co.bradreed.trolleygame.structs

data class Point(val x: Int, val y: Int) {
    operator fun plus(p: Vector) = Point(
            x = x + p.dx,
            y = y + p.dy
    )
}

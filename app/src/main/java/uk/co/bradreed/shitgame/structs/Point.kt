package uk.co.bradreed.trolleygame.structs

data class Point(val x: Int, val y: Int) {
    operator fun plus(p: Point) = Point(
            x = x + p.x,
            y = y + p.y
    )
}

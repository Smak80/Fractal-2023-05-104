package drawing.convertation

data class Plane(
    var xMin: Double,
    var xMax: Double,
    var yMin: Double,
    var yMax: Double,
    var width: Float,
    var height: Float,
) {
    val xDen: Double
        get() = width/(xMax-xMin)
    val yDen: Double
        get() = height/(yMax-yMin)
    val xLen: Double
        get() = xMax - xMin
    val yLen: Double
        get() = yMax - yMin
    val dXY: Double
        get() = xLen * 1.0 / yLen
    val dWH: Double
        get() = width * 1.0 / height

}
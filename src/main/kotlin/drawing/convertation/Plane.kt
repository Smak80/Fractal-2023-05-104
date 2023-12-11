package drawing.convertation

import kotlin.math.abs

data class Plane(
    private  val _xMin: Double,
    private  val _xMax: Double,
    private  val _yMin: Double,
    private  val _yMax: Double,
    private var _width: Float,
    private var _height: Float,
) {

    var xMin: Double = 0.0
        get() = field
        set(value) {
            field = value
            // Можно добавить дополнительные действия при установке значения, если необходимо
        }

    var xMax: Double = 0.0
        get() = field
        set(value) {
            field = value
            // Дополнительные действия при установке значения
        }

    var yMin: Double = 0.0
        get() = field
        set(value) {
            field = value
            // Дополнительные действия при установке значения
        }

    var yMax: Double = 0.0
        get() = field
        set(value) {
            field = value
            // Дополнительные действия при установке значения
        }
    var width: Float
        get() = _width
        set(value) {
            val centerX = (xMin + xMax) / 2.0
            val halfNewWidth = value / 2.0

            // Изменяем границы xMin и xMax относительно центра и новой ширины
            xMin = centerX - halfNewWidth
            xMax = centerX + halfNewWidth

            // Обновляем ширину
            _width = value
        }

    var height: Float
        get() = _height
        set(value) {
            val centerY = (yMin + yMax) / 2.0
            val halfNewHeight = value / 2.0

            // Изменяем границы yMin и yMax относительно центра и новой высоты
            yMin = centerY - halfNewHeight
            yMax = centerY + halfNewHeight

            // Обновляем высоту
            _height = value
        }

    private fun scoping() {
        val X = abs(xMax - xMin) / width
        val Y = abs(yMax - yMin) / height
        if (Y > X) {
            val dx = (width * Y - abs(xMax - xMin)) / 2
            xMin = _xMin - dx
            xMax = _xMax + dx
            yMax = yMax
            yMin = yMin
        } else {
            val dy = (height * X - abs((yMax - yMin))) / 2
            yMin = yMin - dy
            yMax = yMax + dy
            xMax = xMax
            xMin = xMin
        }
    }

    val xDen: Double
        get() = _width / (xMax - xMin)

    val yDen: Double
        get() = _height / (yMax - yMin)
}

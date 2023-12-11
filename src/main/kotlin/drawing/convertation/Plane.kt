package drawing.convertation

data class Plane(
    var xMin: Double,
    var xMax: Double,
    var yMin: Double,
    var yMax: Double,
    private var _width: Float,
    private var _height: Float,
) {
    var width: Float
        get() = _width
        set(value) {
            // Устанавливаем новое значение ширины
            _width = value
            // Обновляем высоту, чтобы сохранить пропорции
            updateHeight()
        }

    var height: Float
        get() = _height
        set(value) {
            // Устанавливаем новое значение высоты
            _height = value
            // Обновляем ширину, чтобы сохранить пропорции
            updateWidth()
        }

    private fun updateWidth() {
        _width = (_height * (xMax - xMin) / (yMax - yMin)).toFloat()
    }

    private fun updateHeight() {
        _height = (_width * (yMax - yMin) / (xMax - xMin)).toFloat()
    }

    val xDen: Double
        get() = _width / (xMax - xMin)

    val yDen: Double
        get() = _height / (yMax - yMin)
}

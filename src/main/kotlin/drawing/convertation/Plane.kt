package drawing.convertation

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


class Plane(
    xMin: Double,
    xMax: Double,
    yMin: Double,
    yMax: Double,
    width: Float,
    height: Float
) {

    private var _xMin = xMin
    private var _xMax = xMax
    private var _yMin = yMin
    private var _yMax = yMax
    private var _width = width.coerceAtLeast(1f) - 1f
    private var _height = height.coerceAtLeast(1f) - 1f

    //Изменяемые Истории (С доступом к изменению)
    var xEdges: Edges
        get() = Edges(_xMin, _xMax)
        set(value) {
            val correctedEdges = correctEdges(value)
            _xMin = correctedEdges.min
            _xMax = correctedEdges.max
            proportions()
        }
    var yEdges: Edges
        get() = Edges(_yMin, _yMax)
        set(value) {
            val correctedEdges = correctEdges(value)
            _yMin = correctedEdges.min
            _yMax = correctedEdges.max
            proportions()
        }

    var width: Float
        get() = _width + 1
        set(value) {
            _width = (value - 1).coerceAtLeast(1f)
        }

    var height: Float
        get() = _height + 1
        set(value) {
            _height = (value - 1).coerceAtLeast(1f)
        }

    init {
        xEdges = Edges(xMin,xMax)
        yEdges = Edges(yMin,yMax)
        this.width = width
        this.height = height

    }
    constructor(plane: Plane) : this(
        plane.xMin,
        plane.xMax,
        plane.yMin,
        plane.yMax,
        plane.width + 1,
        plane.height + 1
    )

    val xMin: Double
        get() = _xMin

    val xMax: Double
        get() = _xMax

    val yMin: Double
        get() = _yMin

    val yMax: Double
        get() = _yMax

    val xDen: Double
        get() = _width.toDouble() / (_xMax - _xMin)

    val yDen: Double
        get() = _height.toDouble() / (_yMax - _yMin)

    private fun correctEdges(p: Edges): Edges =
        if (p.min > p.max) Edges(p.max, p.min) else Edges(p.min, p.max)

    fun copy(): Plane = Plane(this)
    data class Edges(var min:Double,var max:Double)

    private fun proportions()
    {
        val x2w = abs(_xMax-_xMin) /width
        val y2h = abs(_yMax-_yMin) /height
        if(y2h>x2w)
        {
            val dx = (width*y2h- abs(_xMax-_xMin))/2.0
            _xMin -= dx
            _yMax += dx
        }
        else
        {
            val dy = (height*x2w- abs((_yMax-_yMin)))/2.0
            _yMax = max(_yMin,_yMax)+dy
            _yMin = min(_yMin,_yMax)-dy
        }
    }
}


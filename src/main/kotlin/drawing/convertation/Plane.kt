package drawing.convertation

import java.awt.Dimension
import kotlin.math.abs


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
            scoping(this,_xMin,_xMax,_yMin,_yMax,_width,_height)
        }
    var yEdges: Edges
        get() = Edges(_yMin, _yMax)
        set(value) {
            val correctedEdges = correctEdges(value)
            _yMin = correctedEdges.min
            _yMax = correctedEdges.max
            scoping(this,_xMin,_xMax,_yMin,_yMax,_width,_height)
        }

    var width: Float
        get() = _width + 1
        set(value) {
            _width = (value - 1).coerceAtLeast(1f)
            scoping(this,_xMin,_xMax,_yMin,_yMax,_width,_height)
        }

    var height: Float
        get() = _height + 1
        set(value) {
            _height = (value - 1).coerceAtLeast(1f)
            scoping(this,_xMin,_xMax,_yMin,_yMax,_width,_height)
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

    fun scoping(plane : Plane, x0: Double, x1:Double, y0:Double, y1:Double,width: Float,height: Float)
    {
        val x2w = abs(x1-x0) /width
        val y2h = abs(y1-y0) /height
        if(y2h>x2w)
        {
            val dx = (width*y2h- abs(x1-x0))/2.0
            plane.xEdges= Edges(x0-dx,x1+dx)
            plane.yEdges= Edges(y0,y1)
        }
        else
        {
            val dy = (height*x2w- abs((y1-y0)))/2.0
            plane.yEdges=Edges(y0.coerceAtLeast(y1) +dy, y1.coerceAtMost(y0) -dy)
            plane.xEdges=Edges(x0,x1)
        }
    }
}


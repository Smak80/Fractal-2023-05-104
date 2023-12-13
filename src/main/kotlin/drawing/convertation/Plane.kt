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
}


//import java.awt.Dimension
//import kotlin.math.abs
//import kotlin.math.max
//import kotlin.math.min
//
//
//class Plane(
//    xMin: Double,
//    xMax: Double,
//    yMin: Double,
//    yMax: Double,
//    width: Float,
//    height: Float
//) {
//
//    private var _xMin = xMin
//    private var _xMax = xMax
//    private var _yMin = yMin
//    private var _yMax = yMax
//     var width = width
//     var height = height
//
//    //Изменяемые Истории (С доступом к изменению)
//    var xEdges: Edges
//        get() = Edges(_xMin, _xMax)
//        set(value) {
//            val correctedEdges = correctEdges(value)
//            _xMin = correctedEdges.min
//            _xMax = correctedEdges.max
//            println(this)
//        }
//    var yEdges: Edges
//        get() = Edges(_yMin, _yMax)
//        set(value) {
//            val correctedEdges = correctEdges(value)
//            _yMin = correctedEdges.min
//            _yMax = correctedEdges.max
//            println(this)
//        }
//
//    init {
//        this.width = width
//        this.height = height
//        xEdges = Edges(xMin,xMax)
//        yEdges = Edges(yMin,yMax)
//
//        println(this)
//    }
//    constructor(plane: Plane) : this(
//        plane.xMin,
//        plane.xMax,
//        plane.yMin,
//        plane.yMax,
//        plane.width ,
//        plane.height,
//    )
//
//    val xMin: Double
//        get() = _xMin
//
//    val xMax: Double
//        get() = _xMax
//
//    val yMin: Double
//        get() = _yMin
//
//    val yMax: Double
//        get() = _yMax
//
//    val xDen: Double
//        get() = width.toDouble() / (_xMax - _xMin)
//
//    val yDen: Double
//        get() = height.toDouble() / (_yMax - _yMin)
//
//    private fun correctEdges(p: Edges): Edges =
//        if (p.min > p.max) Edges(p.max, p.min) else Edges(p.min, p.max)
//
//    fun copy(): Plane = Plane(this)
//
////    private fun proportions()
////    {
////        val x2w = abs(_xMax-_xMin) /width
////        val y2h = abs(_yMax-_yMin) /height
////        if(y2h>x2w)
////        {
////            _xMin -= dx
////            _yMax += dx
////        }
////        else
////        {
////            _yMax = max(_yMin,_yMax)+dy
////            _yMin = min(_yMin,_yMax)-dy
////        }
////    }
////
////    val dx: Double
////        get() {
////            val xLen = _xMax - _xMin
////            val yLen = _yMax - _yMin
////            val dxy = xLen/yLen
////            val dwh = width/height
////            if(dxy < dwh)
////                return xLen*(dwh/dxy - 1.0)/2.0
////            else
////                return 0.0
////        }
////    val dy: Double
////        get() {
////            val xLen = _xMax - _xMin
////            val yLen = _yMax - _yMin
////            val dxy = xLen/yLen
////            val dwh = width/height
////            if(dxy > dwh)
////                return yLen*(dxy/dwh - 1.0)/2.0
////            else
////                return 0.0
////        }
//
//
//    override fun toString(): String = "xMin:$_xMin xMax:$_xMax yMin:$_yMin xMax:$_yMax width: $width height: $height"
//    data class Edges(var min:Double,var max:Double)
//}
//
//fun makeOneToOne(plane : Plane, x0: Double, x1:Double, y0:Double, y1:Double, dimension: Dimension,)
//{
//    if(dimension.width.toDouble() < 10 || dimension.width.toDouble() < 10) return
//    val x2w = abs(x1-x0) /dimension.width
//    val y2h = abs(y1-y0) /dimension.height
//    if(y2h>x2w)
//    {
//        val dx = (dimension.width*y2h-Math.abs(x1-x0))/2.0
//        plane.xEdges= Plane.Edges(x0-dx,x1+dx)
//        plane.yEdges= Plane.Edges(y0,y1)
//    }
//    else
//    {
//        val dy = (dimension.height*x2w- abs((y1-y0)))/2.0
//        plane.yEdges= Plane.Edges(Math.max(y0,y1)+dy,Math.min(y1,y0)-dy)
//        plane.xEdges= Plane.Edges(x0,x1)
//    }
//}
//
//

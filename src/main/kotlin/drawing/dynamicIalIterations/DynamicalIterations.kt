package drawing.dynamicalIterations

import androidx.compose.runtime.MutableState
import drawing.painters.FractalPainter


class DynamicalIterations(var fp: MutableState<FractalPainter>) {
    private var currentXmin: Double = 0.0
    private var currentXmax: Double = 0.0
    private var currentYmin: Double = 0.0
    private var currentYmax: Double = 0.0
    private var currentPlaneSquare: Double = 0.0

    var maxIterations = 0
    init {
        fp.value.plane?.let { plane ->
            currentXmin = plane.xMin
            currentXmax = plane.xMax
            currentYmin = plane.yMin
            currentYmax = plane.yMax
        }
        currentPlaneSquare = (currentXmax - currentXmin) * (currentYmax - currentYmin)
    }

//    fun changeIterations(){
//        val newMaxIterations = ( lastPlaneSquare/ currentPlaneSquare * 3000).toInt()
//        fp.value.fractal.maxIterations += newMaxIterations
//    }

    fun changeIterations(){
        val newMaxIterations = ( (2000 / currentPlaneSquare)).toInt()
        fp.value.fractal.maxIterations = newMaxIterations
    }

//    fun recreatingPlane() {
//        fp.value.plane?.let {
//            val temp = Plane(it.xMin, it.xMax, it.yMin, it.yMax, it.width, it.height)
//            lastPlane.value = temp
//        }
//    }

}


fun turnDynamicIterations(checkedState: MutableState<Boolean>, fp: MutableState<FractalPainter>) {
    if (checkedState.value) {
        val temp = DynamicalIterations(fp)
        temp.changeIterations()
        //temp.recreatingPlane()
    }
}
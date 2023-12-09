package math.fractals
import androidx.compose.ui.geometry.Offset
import math.Complex

//    companion object {
//        var selectedPoint =
//    }

class Julia(var selectedPoint: Complex, override var function: (Complex) -> Complex) : AlgebraicFractal {
    override var maxIterations: Int = 2000
        private var r = 2.0
        val r2 = r * r
    override fun isInSet(c : Complex) : Float {
        var z: Complex  = c
        for(i in 0..maxIterations){
            z = z * z + Complex(selectedPoint.re.toDouble(), selectedPoint.im.toDouble())
            if (z.abs2() >= r2)
                return i.toFloat() / maxIterations
        }
        return 1f
    }
}
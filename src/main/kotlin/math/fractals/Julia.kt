package math.fractals
import androidx.compose.ui.geometry.Offset
import drawing.convertation.Converter
import math.Complex


//object Julia : AlgebraicFractal {
//    var selectedPoint = Complex()
//    override var maxIterations: Int = 5000
//        set(value) {
//            field = value.coerceIn(20..10000)
//        }
//    var r = 2.0
//    val r2 = r * r
//    override var function: (Complex) -> Complex = { value: Complex -> value }
//        set(value) {
//            if (funcs.containsValue(value)) {
//                field = value
//            }
//        }
//
//
//    override fun isInSet(c: Complex): Float {
//        var z: Complex = c
//        for (i in 0..maxIterations) {
//            z = z * z + Complex(selectedPoint.re, selectedPoint.im)
//            if (z.abs2() >= r2)
//                return i.toFloat() / maxIterations
//        }
//        return 1f
//    }
//
//}

object Julia : AlgebraicFractal {
    var selectedPoint = Complex(0.0,0.0)
    override var maxIterations: Int = 1000
        set(value) { field = value.coerceIn(20..10000)}

    private var r = 2.0
    override fun isInSet(c : Complex) : Float {
        var cnt = 0
        var zn = c
        val r2 = r * r
        while (cnt++ <= maxIterations){
            zn = zn * zn + selectedPoint
            zn = zn*zn + selectedPoint
            if (zn.abs2() >= r2)
                return cnt.toFloat() / maxIterations
        }
        return 1f
    }
}
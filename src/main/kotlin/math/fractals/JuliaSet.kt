package math.fractals

import math.Complex

object JuliaSet : AlgebraicFractal {
    var selectedPoint = Complex(0.0,0.0)
    override var maxIterations: Int = 200
        set(value) { field = value.coerceIn(20..10000)}
    private var r = 2.0
    override fun isInSet(c : Complex) : Float {
        var cnt = 0
        var zn = c
        val r2 = r * r
        while (cnt++ <= maxIterations){
            zn = zn*zn + selectedPoint
            if (zn.abs2() >= r2)
                return cnt.toFloat() / maxIterations
        }
        return 1f
    }
}
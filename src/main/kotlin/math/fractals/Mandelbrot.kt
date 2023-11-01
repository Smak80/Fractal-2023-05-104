package math.fractals

import math.Complex

object Mandelbrot : AlgebraicFractal {
    override var maxIterations: Int = 200
        set(value) { field = value.coerceIn(20..10000)}
    var r = 2.0
    override fun isInSet(c: Complex): Boolean {
        val z = Complex()
        val r2 = r*r
        for (i in 1..maxIterations){
            z*=z
            z+=c
            if (z.abs2() >= r2)
                return false
        }
        return true
    }

}
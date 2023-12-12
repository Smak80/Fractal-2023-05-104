package math.fractals

import math.Complex

object Fractal : AlgebraicFractal {
    override var maxIterations: Int = 1000
        set(value) { field = value.coerceIn(20..50000)}
    var r = 2.0
    var function: (Complex) -> Complex = { value:Complex -> value }
        set(value) {
            if(funcs.containsValue(value)){
                field = value
            }
        }


    override fun isInSet(c: Complex): Float {
        var z = Complex()
        val r2 = r * r
        for (i in 1..maxIterations){
            z = function(z)
            z = z + c
            if (z.abs2() >= r2)
                return i.toFloat()/ maxIterations
        }
        return 1f
    }
}
package math.fractals

import math.Complex

object Fractal : AlgebraicFractal {
    override var maxIterations: Int = 5000
        set(value) { field = value.coerceIn(20..10000)}
    var r = 2.0
    override var function: (Complex) -> Complex = { value:Complex -> value }
        set(value) {
            if(funcs.containsValue(value)){
                field = value
            }
        }


    override fun isInSet(c: Complex): Float {
        val z = Complex()
        val r2 = r * r
        for (i in 1..maxIterations){
            z*= function(z)
            z+=c
            if (z.abs2() >= r2)
                return i.toFloat()/ maxIterations
        }
        return 1f
    }

}
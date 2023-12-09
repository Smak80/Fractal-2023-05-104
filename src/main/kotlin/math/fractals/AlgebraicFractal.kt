package math.fractals

import math.Complex

interface AlgebraicFractal {
    var maxIterations: Int
    var function: (Complex) -> Complex

    fun isInSet(c: Complex): Float

}
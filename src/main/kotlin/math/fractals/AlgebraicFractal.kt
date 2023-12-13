package math.fractals

import math.Complex

interface AlgebraicFractal {

    var maxIterations: Int
    fun isInSet(c: Complex): Float

}
package math.fractals

import math.Complex

object Mandelbrot : AlgebraicFractal {
    var funcNum: FractalFunks = FractalFunks.Classic
    val r = 2.0
    override var maxIterations: Int = 200
    override fun isInSet(c: Complex): Float {
        var i = 0
        val z1 = Complex()
        val r2 = r * r

        do { fractalFunks[funcNum.value]?.invoke(z1)
            z1 += c
        }while (++i < maxIterations && z1.abs2() < r2)

        return i / maxIterations.toFloat()

    }
}
package math.fractals

import math.Complex

object JuliaSet : AlgebraicFractal {
    var selectedPoint = Complex(0.0,0.0)
    override var maxIterations: Int = 200
        set(value) { field = value.coerceIn(20..10000)}
    private var r = 2.0
    override fun isInSet(c : Complex) : Float {
        var i = 0
        val r2 = r * r

        do {
            fractalFunks[Mandelbrot.funcNum.value]?.invoke(c)
            c += selectedPoint
        }while (++i < Mandelbrot.maxIterations && c.abs2() < r2)

        return i / maxIterations.toFloat()

    }
}
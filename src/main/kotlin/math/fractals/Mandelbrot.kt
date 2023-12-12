package math.fractals
import math.Complex

object Mandelbrot : AlgebraicFractal {
    var funcNum: Int = 0
    val r = 2.0
    override var maxIterations: Int = 100
    override fun isInSet(c: Complex): Float {
        var i = 0
        val z1 = Complex()
        val r2 = r * r

        funcNum.let {// классический Мандельброт
            when (it) {
                1 -> {
                    val func1 = {
                        do {
                            z1 *= z1
                            z1 += c
                        } while (++i < maxIterations && z1.abs2() < r2)
                        i / maxIterations.toFloat()
                    }
                    return func1.invoke()
                }
                2 -> {   // Мандельброт перевертыш
                    val func2 = {
                        do {
                            z1 *= z1
                            z1 -= c
                        } while(++i < maxIterations && z1.abs2() < r2)
                        i / maxIterations.toFloat()
                    }
                    return func2.invoke()
                }
                3 -> {   // кубический Мандельброт
                    val func3 = { do{
                        z1 *= z1
                        z1 *= z1
                        z1 += c
                    } while(++i < maxIterations && z1.abs2() < r2)
                        i / maxIterations.toFloat()}
                    return func3.invoke()
                }
                else -> {   // дурацкий кружок
                    val func4 = {
                        do {
                            z1 += z1
                            z1 += c
                        } while(++i < maxIterations && z1.abs2() < r2)
                        i / maxIterations.toFloat()}
                    return func4.invoke()
                }
            }
        }
    }
}
package math.fractals
import math.Complex
import kotlin.math.sin

object Mandelbrot : AlgebraicFractal {
    var funcNum: Int = 0
    val r = 2.0
    override var maxIterations: Int = 200
    override fun isInSet(c: Complex): Float {
        var i = 0
        val z1 = Complex()
        val r2 = r * r

        do { fractalFunks[funcNum]?.invoke(z1,c)
        }while (++i < maxIterations && z1.abs2() < r2)

        return i / maxIterations.toFloat()

    }
}

val fractalFunks2: Map<Int,(Complex) -> Float> = mapOf(

)



val fractalFunks: Map<Int, (Complex, Complex) -> Unit> = mapOf(
    1 to { z, c ->
        z *= z
        z += c
    },
    2 to { z, c->
        z -= (z * z * z - Complex(1.0,0.0)).div((z * z * 3.0))
    },
    3 to { z, c ->
        z *= z
        z *= z
        z += c
    },
    4 to { z, c ->
        z += z
        z += c
    },
    5 to { z, c ->
        z -= (z * z * z - Complex(1.0,0.0)).div((z * z * 3.0))
        z += c
    }

)

package math.fractals

import math.Complex
import kotlin.math.cos
import kotlin.math.cosh
import kotlin.math.sin
import kotlin.math.sinh

enum class FractalFunks(val value: Int) {
    Zero(0),
    Classic(1),
    Cube(2),
    FourthP(3),
    Jora(4),
    Sin(5),
    Dino(6),
    Fat(7),
}

val fractalFunks: Map<Int, (Complex) -> Unit> = mapOf(
    1 to { z ->
        z *= z
    },
    2 to { z->
        z *= z * z
    },
    3 to { z ->
        z *= z * z * z
    },
    4 to { z ->
        z += z*z
    },
    5 to { z ->
        val sinZ = Complex(sin(z.re) * cosh(z.im), cos(z.re) * sinh(z.im))
        z *= sinZ
    },
    6 to { z ->
        val zSquared = z * z
        z.re = sin(zSquared.re) * cosh(2 * zSquared.im)
        z.im = cos(2 * z.re) * sinh(zSquared.im)
    },
    7 to { z ->
        z *= z*z*z*z*z*z*z*z*z*z*z*z*z
    },
    0 to { z ->
        z += z
    }
)

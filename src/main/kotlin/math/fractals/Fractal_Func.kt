package math.fractals

import math.Complex

val funcs: Map<String, (Complex) -> Complex> = mapOf(
    "Mandelbrot" to {z: Complex -> z * z},
    "square" to {z: Complex -> z * z * z * z * z },
    "third_pow" to {z: Complex -> z * z * z },
    "multiply_and_plus" to {z: Complex ->  z * z * z * z * z + z * z * z * z}
)
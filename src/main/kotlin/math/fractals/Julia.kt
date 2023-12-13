package math.fractals
import math.Complex

object Julia : AlgebraicFractal {
    var selectedPoint = Complex(0.0,0.0)
    override var function: (Complex) -> Complex = { value:Complex -> value }
        set(value) {
            if(funcs.containsValue(value)){
                field = value
            }
        }
    override var maxIterations: Int = 2000
    private var r = 2.0
    private val r2 = r * r
    override fun isInSet(c : Complex) : Float {
        var z: Complex  = c
        for(i in 0..maxIterations){
            z = function(z) + selectedPoint
            if (z.abs2() >= r2)
                return i.toFloat() / maxIterations
        }
        return 1f
    }
}
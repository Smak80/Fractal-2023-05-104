package math.fractals

import drawing.convertation.ColorFuncs
import java.io.Serializable

data class FractalData(
    val xMin:Double,
    val xMax:Double,
    val yMin:Double,
    val yMax:Double,
    val colorscheme: ColorFuncs,
    val fractalFunk: FractalFunks,
) : Serializable
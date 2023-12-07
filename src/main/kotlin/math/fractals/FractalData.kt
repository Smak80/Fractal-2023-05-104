package math.fractals

import androidx.compose.ui.graphics.Color
import java.io.Serializable

data class FractalData(
    val xMin:Double,
    val xMax:Double,
    val yMin:Double,
    val yMax:Double,
    val colorscheme: Int
) : Serializable
import androidx.compose.ui.graphics.Color

data class FractalData(
    val xMin:Double,
    val xMax:Double,
    val yMin:Double,
    val yMax:Double,
    val colorscheme:(Color)->Unit
)
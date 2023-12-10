package video

import androidx.compose.ui.graphics.Color
import drawing.convertation.ColorType
import math.Complex

data class VideoConfiguration(
    val width: Float,
    val height: Float,
    val duration: Int,
    val fps: Int = 24,
    val file: String,
    val cadres: MutableList<Cadre>,
    val colorScheme: ColorType
)

package video

import androidx.compose.ui.graphics.Color
import math.Complex

data class VideoConfiguration(
    val width: Float,
    val height: Float,
    val duration: Int,
    val fps: Int = 24,
    val file: String,
    val cadres: MutableList<Cadre>,
    val colorScheme: (Float) -> Color
)

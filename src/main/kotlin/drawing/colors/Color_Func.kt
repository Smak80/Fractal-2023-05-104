package drawing.colors

import androidx.compose.ui.graphics.Color
import kotlin.math.*

val colors: Map<String, (Float) -> Color> = mapOf(

    "color1" to {
        if (it == 1f) Color.Black
        else {
            val r = sin(it*15f).absoluteValue
            val g = (sin(-8f*it) * cos(it*5f+12f)).absoluteValue
            val b = log2(2f - cos(sin(18*-it)))
            Color(r, g, b)
        }
    },                                      // стандартная раскраска с пары

    "color2" to {
        if (it == 1f) Color.Black
        else {
            val r = cos(it + PI *(0.5 + it)).absoluteValue.toFloat()
            val g = (2* atan(it + PI *(tan(it))) / PI).absoluteValue.toFloat()
            val b = cos(it+ PI *(0.5+ sin(it))).absoluteValue.toFloat()
            Color(r, g, b)
        }
    },

    "color3" to {
        if (it == 1f) Color.Black
        else {
            val r = (0.5f * (1 - cos(16f * it * it)) * sin(-12*it)).absoluteValue
            val g = sin(5f * it).absoluteValue  * cos(5f * it).absoluteValue
            val b = log10(1f + 5 * it)
            Color(r, g, b)
        }
    }
)
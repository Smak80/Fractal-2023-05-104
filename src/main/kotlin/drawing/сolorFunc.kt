package drawing.convertation

import androidx.compose.ui.graphics.Color
import kotlin.math.*

enum class ColorFuncs(val value: Int) {
    Zero(0),
    First(1),
    Second(2),
    Third(3)
}
fun colorFunc(colorNumber: Int): (Float) -> Color{
    val color0: (Float) -> Color = {if (it < 1f) Color.White else Color.Black }
    val color1: (Float) -> Color =
        { if (it == 1f) Color.Black else
            Color(0.5f*(1- cos(255*it*it)).absoluteValue,
                sin(105*it).absoluteValue,
                log10((255 + it)/100).absoluteValue)
        }
    val color2: (Float) -> Color =
    { if (it == 1f) Color.Black else
        Color(sin(144 * it).absoluteValue,
            cos(180*it).absoluteValue,
            0.5f + 0.08f * cos((30*it * it)).absoluteValue)
    }
    val color3: (Float) -> Color =
    { if (it == 1f) Color.Black else
        Color(cos(180*it).absoluteValue,
            (sin(-8f*it) * cos(it*5f+12f)).absoluteValue,
            0.05f*(1- cos(255*it*it)).absoluteValue)
    }
    return when(colorNumber){
        1 -> color1
        2 -> color2
        3 -> color3
        else -> color0
    }
}

package drawing.convertation

import androidx.compose.ui.graphics.Color
import kotlin.math.*

fun colorFunc(colorNumber: Int,
              color1: (Float) -> Color =
                  { if (it == 1f) Color.Black else
                      Color(0.5f*(1- cos(255*it*it)).absoluteValue,
                          sin(105*it).absoluteValue,
                          log10((255 + it)/100).absoluteValue)
                  },
              color2: (Float) -> Color =
                  { if (it == 1f) Color.Black else
                      Color(sin(144 * it).absoluteValue,
                          cos(180*it).absoluteValue,
                          0.5f + 0.08f * cos((30*it * it)).absoluteValue)
                  },
              color3: (Float) -> Color =
                  { if (it == 1f) Color.Black else
                      Color(cos(180*it).absoluteValue,
                          (sin(-8f*it) * cos(it*5f+12f)).absoluteValue,
                          0.05f*(1- cos(255*it*it)).absoluteValue)
                  }): (Float) -> Color {
    if (colorNumber==1) return color1
    else if (colorNumber==2) return color2
    else return color3
}
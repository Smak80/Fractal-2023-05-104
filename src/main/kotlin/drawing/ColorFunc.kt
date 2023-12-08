package drawing.convertation

import androidx.compose.ui.graphics.Color
import kotlin.math.*

fun ColorFunc(ColorNumber: Int,
              Color1: (Float) -> Color =
                  { if (it == 1f) Color.Black else
                      Color(0.5f*(1- cos(255*it*it)).absoluteValue,
                          sin(105*it).absoluteValue,
                          log10((255 + it)/100).absoluteValue)
                  },
              Color2: (Float) -> Color =
                  { if (it == 1f) Color.Black else
                      Color(sin(144 * it).absoluteValue,
                          cos(180*it).absoluteValue,
                          0.5f + 0.08f * cos((30*it * it)).absoluteValue)
                  },
              Color3: (Float) -> Color =
                  { if (it == 1f) Color.Black else
                      Color(cos(180*it).absoluteValue,
                          (sin(-8f*it) * cos(it*5f+12f)).absoluteValue,
                          0.05f*(1- cos(255*it*it)).absoluteValue)
                  }): (Float) -> Color {
    if (ColorNumber==1) return Color1
    else if (ColorNumber==2) return Color2
    else return Color3
}
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import controls.DrawingPanel
import controls.SelectionPanel
import controls.mainFractalWindow
import drawing.FractalPainter
import drawing.Painter
import drawing.SelectionRect
import drawing.convertation.Converter
import drawing.convertation.Plane
import math.fractals.Mandelbrot
import kotlin.math.*

@Composable
@Preview
fun App(){
    val fp = remember { FractalPainter(Mandelbrot){
        if (it == 1f) Color.Black
        else {
            val r = sin(it*15f).absoluteValue
            val g = (sin(-8f*it)* cos(it*5f+12f)).absoluteValue
            val b = log2(2f - cos(sin(18*-it)))
            Color(r, g, b)
        }
    }}
    fp.plane = Plane(-2.0, 1.0, -1.0, 1.0, 0f, 0f)
    MaterialTheme{
        Scaffold(
            topBar = {
                // TopAppBar содержит компоненты AppBar, такие как IconButton и Text
                TopAppBar(
                    title = {
                        Text(text = "SDsfasdasdasd")
                    },
                    navigationIcon = {
                        // NavigationIcon представляет значок для кнопки навигации
                        IconButton(
                            onClick = {
                                // Обработка события нажатия на кнопку навигации
                            }
                        ) {
                        }
                    },
                    actions = {
                        // Список действий, расположенных справа от заголовка
                        IconButton(
                            onClick = {
                                // Обработка события нажатия на первую кнопку
                            }
                        ) {
                        }
                        IconButton(
                            onClick = {
                                // Обработка события нажатия на вторую кнопку
                            }
                        ) {
                        }
                    }
                )
            },
            content = {
                mainFractalWindow(fp)
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}




fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Множество Мандельброта"
    ) {
        App()
    }
}


import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import drawing.FractalPainter
import drawing.convertation.Plane
import math.fractals.Mandelbrot
import kotlin.math.*

@Composable
@Preview
fun App() {
    val fp = FractalPainter(Mandelbrot){
        if (it == 1f) Color.Black
        else {
            val g = sin(it*7f).absoluteValue
            val b = (sin(-8f*it)* cos(it*5f+2f)).absoluteValue
            val r = log2(2f - cos(sin(13*-it)))
            Color(r, g, b)
        }
    }
    fp.plane = Plane(-2.0, 1.0, -1.0, 1.0, 0f, 0f)
    MaterialTheme {
        Canvas(Modifier.fillMaxSize().padding(8.dp)){
            fp.width = size.width.toInt()
            fp.height = size.height.toInt()
            fp.paint(this)
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

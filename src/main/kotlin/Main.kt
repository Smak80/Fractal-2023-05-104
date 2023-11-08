import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import drawing.FractalPainter
import drawing.SelectionRect
import drawing.convertation.Plane
import math.fractals.Mandelbrot
import kotlin.math.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun App() {
    var rect by mutableStateOf(SelectionRect(Offset.Zero))
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
        Canvas(Modifier.fillMaxSize().padding(8.dp).pointerInput(Unit){
            detectDragGestures(
                onDragStart = {
                    rect = SelectionRect(it)
                              },
                onDrag = {
                    rect.addPoint(it)
                         },
                matcher = PointerMatcher.Primary)
        }){
            fp.width = size.width.toInt()
            fp.height = size.height.toInt()
            fp.paint(this)
            drawRect(Color(1f, 1f, 1f, 0.3f), rect.topLeft, rect.size)

        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

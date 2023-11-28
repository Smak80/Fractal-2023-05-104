import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import drawing.FractalPainter
import drawing.Painter
import drawing.SelectionRect
import drawing.convertation.Converter
import drawing.convertation.Plane
import math.fractals.Mandelbrot
import kotlin.math.*

@Composable
@Preview
fun App() {

    // инициализация объекта fp
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


    MaterialTheme {
        DrawingPanel(fp){size ->
            fp.width = size.width.toInt()
            fp.height = size.height.toInt()
            fp.refresh = true
        }
        SelectionPanel{
            fp.plane?.let{ plane ->
                val xMin = Converter.xScr2Crt(it.topLeft.x, plane)
                val xMax = Converter.xScr2Crt(it.topLeft.x+it.size.width, plane)
                val yMax = Converter.yScr2Crt(it.topLeft.y, plane)
                val yMin = Converter.yScr2Crt(it.topLeft.y+it.size.height, plane)
                plane.xMin = xMin
                plane.xMax = xMax
                plane.yMin = yMin
                plane.yMax = yMax
                fp.refresh = true
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectionPanel(
    onSelected: (SelectionRect)->Unit
) {
    var rect by remember {mutableStateOf(SelectionRect(Offset.Zero))}
    Canvas(Modifier.fillMaxSize().padding(8.dp).pointerInput(Unit){
        detectDragGestures(
            onDragStart = {
                rect = SelectionRect(it)
            },
            onDrag = {
                rect.addPoint(it)
            },
            onDragEnd = {
                onSelected(rect)
                rect = SelectionRect(Offset.Zero)
            },
            matcher = PointerMatcher.Primary)
    }){
        drawRect(Color(0f, 1f, 1f, 0.3f), rect.topLeft, rect.size)
    }
}

@Composable
fun DrawingPanel(
    fp: FractalPainter,
    onResize: (Size)-> Unit = {},
) {
    val lastXmin = remember { mutableStateOf(fp.plane!!.xMin) }
    val lastXmax = remember { mutableStateOf(fp.plane!!.xMax) }
    val lastYmin = remember { mutableStateOf(fp.plane!!.yMin) }
    val lastYmax = remember { mutableStateOf(fp.plane!!.yMax) }
    val lastWidth = remember { mutableStateOf(fp.width) }
    val lastHeight = remember { mutableStateOf(fp.height) }

    Canvas(Modifier.fillMaxSize().padding(8.dp)) {
        if(fp.width != size.width.toInt() || fp.height != size.height.toInt() ) {
            onResize(size)

            val deltaX = (lastXmax.value - lastXmin.value) * ((fp.width / lastWidth.value) - 1) / 2
            val deltaY = (lastYmax.value - lastYmin.value) * ((fp.height / lastHeight.value) - 1) / 2

            fp.plane!!.xMin = lastXmin.value - deltaX
            fp.plane!!.xMin = lastXmax.value + deltaX

            fp.plane!!.yMin = lastYmin.value - deltaY
            fp.plane!!.yMax = lastYmax.value + deltaY

            lastXmin.value = fp.plane!!.xMin
            lastXmax.value = fp.plane!!.xMax
            lastYmin.value = fp.plane!!.yMin
            lastYmax.value = fp.plane!!.yMax
            lastWidth.value = fp.width
            lastHeight.value = fp.height

        }
        fp.paint(this)
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
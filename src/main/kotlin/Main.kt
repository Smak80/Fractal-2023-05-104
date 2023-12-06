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
import drawing.colors.colors
import drawing.convertation.Converter
import drawing.convertation.Plane
import math.fractals.Fractal
import math.fractals.funcs


@Composable
@Preview
fun App() {

//    val funcs: Map<String, (Complex) -> Complex> = mapOf(
//        "Mandelbrot" to {z: Complex -> z},
//        "square" to {z:Complex -> z * z * z * z * z},
//        "third_pow" to {z:Complex -> z * z * z},
//        "multiply_and_plus" to {z:Complex -> z * z * z * z * z + z * z * z * z}
//    )

//    val colors: Map<String, (Float) -> Color> = mapOf(
//
//        "color1" to {
//            if (it == 1f) Color.Black
//            else {
//                val r = sin(it*15f).absoluteValue
//                val g = (sin(-8f*it)* cos(it*5f+12f)).absoluteValue
//                val b = log2(2f - cos(sin(18*-it)))
//                Color(r, g, b)
//            }
//        },                                      // стандартная раскраска с пары
//
//        "color2" to {
//            if (it == 1f) Color.Black
//            else {
//                val r = cos(it + PI*(0.5 + it)).absoluteValue.toFloat()
//                val g = (2*atan(it + PI*(tan(it)))/ PI).absoluteValue.toFloat()
//                val b = cos(it+PI*(0.5+sin(it))).absoluteValue.toFloat()
//                Color(r, g, b)
//            }
//        },
//
//        "color3" to {
//            if (it == 1f) Color.Black
//            else {
//                val r = (0.5f * (1 - cos(16f * it * it)) * sin(-12*it)).absoluteValue
//                val g = sin(5f * it).absoluteValue  * cos(5f * it).absoluteValue
//                val b = log10(1f + 5 * it)
//                Color(r, g, b)
//            }
//        }
//    )

    Fractal.function = funcs["Mandelbrot"]!!

    val fp = remember { FractalPainter(Fractal, colors["color2"]!!)}

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
    fp: Painter,
    onResize: (Size)-> Unit = {},
) {
    Canvas(Modifier.fillMaxSize().padding(8.dp)) {
        if(fp.width != size.width.toInt() || fp.height != size.height.toInt() )
            onResize(size)

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
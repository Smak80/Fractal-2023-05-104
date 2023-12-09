import GUI.menu
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import drawing.FractalPainter
import drawing.SelectionRect
import drawing.colors.colors
import drawing.convertation.Plane
import math.fractals.Fractal
import math.fractals.funcs
import java.awt.Dimension


@Composable
@Preview
fun App() {

    val fp = remember { mutableStateOf(FractalPainter(Fractal, colors["color1"]!!))}
    

    fp.value.plane = Plane(-2.0, 1.0, -1.0, 1.0, 0f, 0f)

    fp.value.plane?.let {
        fp.value.xMin = it.xMin
        fp.value.xMax = it.xMax
        fp.value.yMax = it.yMax
        fp.value.yMin = it.yMin
    }

    MaterialTheme {
        menu(fp)
    }
}

fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        title = "Множество Мандельброта"
    ) {
        this.window.minimumSize = Dimension(600, 400)
        App()
    }
}
package controls

import JuliaApp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowSize
import controls.panels.drawingPanel
import drawing.FractalPainter
import drawing.Painter
import drawing.SelectionRect
import drawing.convertation.Converter
import drawing.convertation.Plane
import math.Complex
import math.fractals.JuliaSet
import math.fractals.Mandelbrot
import java.awt.Toolkit
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.log2
import kotlin.math.sin

@Composable
fun mainFractalWindow(fp:FractalPainter){
    drawingPanel(fp){ size ->
        fp.width = size.width.toInt()
        fp.height = size.height.toInt()
        fp.refresh = true
    }
    selectionPanel{
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun selectionPanel(
    onSelected: (SelectionRect)->Unit
) {
    var rect by remember { mutableStateOf(SelectionRect(Offset.Zero)) }
    var juliaDialogVisible by remember { mutableStateOf(false) }
    var pt by remember { mutableStateOf(Offset(0f,0f)) }
    Canvas(Modifier
        .fillMaxSize()
        .pointerInput(Unit){
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
        }
        .pointerInput(Unit) {
            detectTapGestures(
                onTap = { offset ->
                    pt = Offset(offset.x, offset.y)
                    juliaDialogVisible = true
                }
            )
        }
    ){
        drawRect(Color(0f, 1f, 1f, 0.3f), rect.topLeft, rect.size)
    }
    if (juliaDialogVisible) {
        Window(
            onCloseRequest = { juliaDialogVisible = false },
            title = "Множество Жулиа"
        ){
            JuliaApp(pt)
        }
    }
}


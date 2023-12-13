package gui

import JuliaApp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.window.Window
import drawing.FractalPainter
import drawing.SelectionRect
import drawing.convertation.Converter
import math.Complex
import math.fractals.JuliaSet
import tools.ActionStack


@Composable
fun mainFractalWindow(fp: FractalPainter,actionStack: ActionStack){
    var juliaDialogVisible by remember { mutableStateOf(false) }
    fractalWindow(fp,actionStack) { juliaDialogVisible = true }
    if (juliaDialogVisible) {
        Window(
            onCloseRequest = { juliaDialogVisible = false },
            title = "Множество Жулиа"
        ){ JuliaApp(fp) }
    }
}
@Composable
fun fractalWindow(fp:FractalPainter,actionStack: ActionStack ,jiliaInvoker:(() -> Unit)? = null){
    drawingPanel(fp,
        onResize = { size ->
            fp.width = size.width.toInt()
            fp.height = size.height.toInt()
            fp.refresh = true
        },
    )
    selectionPanel(
        onTap = { offset->
            jiliaInvoker?.let{
                fp.plane?.let { plane ->
                    val xCart = Converter.xScr2Crt(offset.x,plane)
                    val yCart = Converter.yScr2Crt(offset.y,plane)
                    JuliaSet.selectedPoint = Complex(xCart,yCart)
                    jiliaInvoker()
                }
            }
        },
        onSelected = {
            fp.plane?.let{ plane ->
                val currConf = ActionStack.CartCords(
                    plane.xMin,
                    plane.xMax,
                    plane.yMin,
                    plane.yMax,
                )
                actionStack.push(currConf)
                val xMin = Converter.xScr2Crt(it.topLeft.x, plane)
                val xMax = Converter.xScr2Crt(it.topLeft.x+it.size.width, plane)
                val yMax = Converter.yScr2Crt(it.topLeft.y, plane)
                val yMin = Converter.yScr2Crt(it.topLeft.y+it.size.height, plane)
                plane.xMax = xMax
                plane.xMin = xMin
                plane.yMax = yMax
                plane.yMin = yMin

//                plane.xMin = xMin
//                plane.xMax = xMax
//                plane.yMin = yMin
//                plane.yMax = yMax
                fp.xMin = xMin
                fp.xMax = xMax
                fp.yMin = yMin
                fp.yMax = yMax
                fp.refresh = true
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun selectionPanel(
    onSelected: (SelectionRect)->Unit,
    onTap: (Offset)->Unit
) {
    var rect by remember { mutableStateOf(SelectionRect(Offset.Zero)) }
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
        .pointerInput(Unit) { detectTapGestures(onTap = { offset -> onTap(offset) }) }

    ){
        drawRect(Color(0f, 1f, 1f, 0.3f), rect.topLeft, rect.size)
    }
}
@Composable
fun drawingPanel(
    fp: FractalPainter,
    onResize: (Size)-> Unit = {},
) {
    Canvas(
        Modifier.fillMaxSize()
    ) {
        if(fp.width != size.width.toInt() || fp.height != size.height.toInt()){
            onResize(size)
        }
        fp.paint(this)
    }
}
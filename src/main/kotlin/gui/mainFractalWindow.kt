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
fun mainFractalWindow(fp: FractalPainter){
    var juliaDialogVisible by remember { mutableStateOf(false) }
    drawingPanel(fp,
        onResize = { size ->
            if(fp.width == 0 || fp.height == 0) {
                fp.width = size.width.toInt()
                fp.height = size.height.toInt()
                fp.refresh = true
            }
            else{
                fp.width = size.width.toInt()
                fp.height = size.height.toInt()
                fp.plane?.let {plane ->
                    var newXMin = plane.xMin
                    var newXMax = plane.xMax
                    var newYMin = plane.yMin
                    var newYMax = plane.yMax
                    if(plane.dXY < plane.dWH){
                        val dx = plane.yLen * plane.dWH - plane.xLen
                        newXMin -= dx/2
                        newXMax += dx/2
                    }
                    if(plane.dXY > plane.dWH){
                        val dy = plane.xLen / plane.dWH - plane.yLen
                        newYMin -= dy/2
                        newYMax += dy/2
                    }
                    plane.xMin = newXMin
                    plane.xMax = newXMax
                    plane.yMin = newYMin
                    plane.yMax = newYMax
                }
                fp.refresh = true
            }
        },
    )
    selectionPanel(
        onTap = {offset->
            fp.plane?.let { plane ->
                val xCart = Converter.xScr2Crt(offset.x,plane)
                val yCart = Converter.yScr2Crt(offset.y,plane)
                JuliaSet.selectedPoint = Complex(xCart,yCart)
                juliaDialogVisible = true
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
                fp.actionStack.push(currConf)
                val xMin = Converter.xScr2Crt(it.topLeft.x, plane)
                var xMax = Converter.xScr2Crt(it.topLeft.x+it.size.width, plane)
                val yMax = Converter.yScr2Crt(it.topLeft.y, plane)
                var yMin = Converter.yScr2Crt(it.topLeft.y+it.size.height, plane)
                //ширина прямоугольника
                var rw = it.size.width.toDouble()
                //высота прямоугольника
                var rh = it.size.height.toDouble()
                if(Math.abs(rw/rh - plane.dWH) > 1E-6){
                    if(rw/rh < plane.dWH){
                        rw = rh * plane.dWH
                        xMax = Converter.xScr2Crt(it.topLeft.x + rw.toInt(), plane)
                    }
                    if(rw/rh > plane.dWH){
                        rh = rw / plane.dWH
                        yMin = Converter.yScr2Crt(it.topLeft.y + rh.toInt(), plane)
                    }
                }
                plane.xMin = xMin
                plane.xMax = xMax
                plane.yMin = yMin
                plane.yMax = yMax
                fp.refresh = true
            }
        }
    )
    if (juliaDialogVisible) {
        Window(
            onCloseRequest = { juliaDialogVisible = false },
            title = "Множество Жулиа"
        ){
            JuliaApp()
        }
    }
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
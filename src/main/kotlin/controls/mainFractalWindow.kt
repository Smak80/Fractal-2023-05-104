package controls

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import drawing.FractalPainter
import drawing.Painter
import drawing.SelectionRect
import drawing.convertation.Converter

@Composable
fun mainFractalWindow(fp:FractalPainter){
    drawingPanel(fp){ size ->
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
    }
    selectionPanel{
        fp.plane?.let{ plane ->
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
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun selectionPanel(
    onSelected: (SelectionRect)->Unit
) {
    var rect by remember { mutableStateOf(SelectionRect(Offset.Zero)) }
    Canvas(Modifier.fillMaxSize().pointerInput(Unit){
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
fun drawingPanel(
    fp: Painter,
    onResize: (Size)-> Unit = {},
) {
    Canvas(Modifier.fillMaxSize()) {
        if(fp.width != size.width.toInt() || fp.height != size.height.toInt() )
            onResize(size)

        fp.paint(this)
    }
}
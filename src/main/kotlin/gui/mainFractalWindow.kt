package gui

import JuliaApp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.window.Window
import drawing.FractalPainter
import drawing.Painter
import drawing.SelectionRect
import drawing.convertation.Converter
import video.Cadre
import java.awt.image.BufferedImage

@Composable
fun mainFractalWindow(fp:FractalPainter,photoList: SnapshotStateList<Cadre>){
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
@Composable
fun drawingPanel(
    fp: Painter,
    onResize: (Size)-> Unit = {},
) {
    Canvas(Modifier.fillMaxSize()) {
        if(fp.width != size.width.toInt() || fp.height != size.height.toInt()){
            onResize(size)
        }
        fp.paint(this)
    }
}
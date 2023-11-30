package controls.panels

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import drawing.Painter

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

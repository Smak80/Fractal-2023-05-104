package drawing.painters

import androidx.compose.ui.graphics.drawscope.DrawScope

interface Painter {
    var width: Int
    var height: Int
    fun paint(scope: DrawScope)
}
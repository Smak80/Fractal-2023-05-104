package drawing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import drawing.convertation.Converter
import drawing.convertation.Plane
import math.Complex
import math.fractals.AlgebraicFractal

class FractalPainter(val fractal: AlgebraicFractal) : Painter {
    var plane: Plane? = null
    override var width: Int
        get() = plane?.width?.toInt() ?: 0
        set(value) { plane?.width = value.toFloat() }
    override var height: Int
        get() = plane?.height?.toInt() ?: 0
        set(value) {plane?.height = value.toFloat()}

    override fun paint(scope: DrawScope) {
        plane?.let {plane ->
            for (i in 0..width)
                for (j in 0..height) {
                    val x = Complex(Converter.xScr2Crt(i.toFloat(), plane), Converter.yScr2Crt(j.toFloat(), plane))
                    if (fractal.isInSet(x))
                        scope.drawRect(Color.Black, Offset(i.toFloat(), j.toFloat()), Size(1f,1f))




                }
        }
    }

}
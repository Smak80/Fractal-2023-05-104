package drawing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.toComposeImageBitmap
import drawing.convertation.Converter
import drawing.convertation.Plane
import math.Complex
import math.fractals.AlgebraicFractal
import java.awt.image.BufferedImage

class FractalPainter(
    val fractal: AlgebraicFractal,
    val colorFunc: (Float) -> Color = {if (it< 1f) Color.White else Color.Black}
) : Painter {
    var plane: Plane? = null
    override var width: Int
        get() = plane?.width?.toInt() ?: 0
        set(value) { plane?.width = value.toFloat() }
    override var height: Int
        get() = plane?.height?.toInt() ?: 0
        set(value) {plane?.height = value.toFloat()}

    override fun paint(scope: DrawScope) {
        val img = BufferedImage(scope.size.width.toInt(), scope.size.height.toInt(), BufferedImage.TYPE_INT_ARGB)
        plane?.let { plane ->
            for (i in 0..<width)
                for (j in 0..<height) {
                    val x = Complex(Converter.xScr2Crt(i.toFloat(), plane), Converter.yScr2Crt(j.toFloat(), plane))
                    img.setRGB(i, j, colorFunc(fractal.isInSet(x)).toArgb())
                    //scope.drawRect(colorFunc(fractal.isInSet(x)), Offset(i.toFloat(), j.toFloat()), Size(1f,1f))
                }
            scope.drawImage(img.toComposeImageBitmap())
        }
    }

}
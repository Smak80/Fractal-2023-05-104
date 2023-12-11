package video

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.toComposeImageBitmap
import com.sun.source.tree.Scope
import drawing.FractalPainter
import drawing.convertation.ColorType
import drawing.convertation.Plane
import math.fractals.Mandelbrot
import java.awt.image.BufferedImage

class Cadre(plane: Plane,colorType: ColorType) {

    val plane: Plane
    var preRenderImg: BufferedImage

    init {
        this.plane = plane.copy()
        preRenderImg = getImageFromPlane(this.plane,150f, 150f,colorType)
       // img = getImageFromPlane(this.plane, 0f, 0f)
    }

    companion object{
        fun getImageFromPlane(plane: Plane, width: Float, height: Float,colorScheme:ColorType): BufferedImage {
            val fp = FractalPainter(Mandelbrot)
            fp.colorFuncID = colorScheme
            fp.plane = plane
            fp.plane?.let {
                fp.xMin = it.xMin
                fp.xMax = it.xMax
                fp.yMax = it.yMax
                fp.yMin = it.yMin
            }
            fp.refresh = true
            fp.prepareForPaint(width.toInt(),height.toInt())
            print(plane)
            return fp.img
        }
    }
}
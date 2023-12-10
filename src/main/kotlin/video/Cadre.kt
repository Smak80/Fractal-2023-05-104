package video

import androidx.compose.ui.graphics.Color
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
            val newPlane = plane.copy()
            newPlane.width = width;
            newPlane.height = height;

            val fp = FractalPainter(Mandelbrot)
            fp.plane = newPlane
            fp.colorFuncID = colorScheme

            val img = BufferedImage(
                width.toInt(),
                height.toInt(),
                BufferedImage.TYPE_INT_RGB
            )

            return fp.getImageFromPlane(img)
        }
    }
}
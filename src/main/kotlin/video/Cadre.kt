package video

import drawing.FractalPainter
import drawing.convertation.ColorFuncs
import drawing.convertation.Plane
import math.fractals.Mandelbrot
import java.awt.image.BufferedImage


class Cadre(plane: Plane, colorFuncs: ColorFuncs) {

    val plane: Plane
    var preRenderImg: BufferedImage

    init {
        this.plane = plane.copy()
        preRenderImg = getImageFromPlane(this.plane,140f, 140f,colorFuncs)
        // img = getImageFromPlane(this.plane, 0f, 0f)
    }

    companion object{
        fun getImageFromPlane(plane: Plane, width: Float, height: Float, colorFuncs: ColorFuncs): BufferedImage {
            val fp = FractalPainter(Mandelbrot)
            fp.initPlane(plane)
            fp.width = width.toInt()
            fp.height = height.toInt()
            fp.refresh = true
            fp.colorFuncID = colorFuncs
            val img = BufferedImage(
                fp.width,
                fp.height,
                BufferedImage.TYPE_INT_RGB
            )
            return fp.getImageFromPlane(img)
        }
    }
}
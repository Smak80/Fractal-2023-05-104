package video

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
        fun getImageFromPlane(plane: Plane, width: Float, height: Float, colorType: ColorType): BufferedImage {

            val fp = FractalPainter(Mandelbrot)
            fp.plane = plane
            fp.xMin = plane.xMin
            fp.xMax = plane.xMax
            fp.yMin = plane.yMin
            fp.yMax = plane.yMax
            fp.width = width.toInt()
            fp.height = height.toInt()
            fp.colorFuncID = colorType

            val img = BufferedImage(
                fp.width.toInt(),
                fp.width.toInt(),
                BufferedImage.TYPE_INT_RGB
            )
            return fp.getImageFromPlane(img)
        }
    }
}
package video

import drawing.FractalPainter
import drawing.convertation.Plane
import math.fractals.Mandelbrot
import java.awt.image.BufferedImage

class Cadre(plane: Plane) {

    val plane: Plane
    //var img: BufferedImage
    var preRenderImg: BufferedImage

    init {
        this.plane = plane
        preRenderImg = getImageFromPlane(this.plane,110f, 110f)
       // img = getImageFromPlane(this.plane, 0f, 0f)
    }

    companion object{
        fun getImageFromPlane(plane: Plane, width: Float, height: Float): BufferedImage {
            plane.width = width;
            plane.height = height;

            val fp = FractalPainter(fractal = Mandelbrot)
            fp.plane = plane

            val img = BufferedImage(
                width.toInt(),
                height.toInt(),
                BufferedImage.TYPE_INT_RGB
            )

            return fp.getImageFromPlane(img)
        }
    }
}
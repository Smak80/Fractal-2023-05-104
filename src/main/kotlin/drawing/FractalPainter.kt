package drawing

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.toComposeImageBitmap
import drawing.convertation.ColorType
import drawing.convertation.Converter
import drawing.convertation.Plane
import drawing.convertation.colorFunc
import math.Complex
import math.fractals.AlgebraicFractal
import math.fractals.Mandelbrot
import tools.ActionStack
import java.awt.image.BufferedImage
import kotlin.concurrent.thread

class FractalPainter(
    private val fractal: AlgebraicFractal
) : Painter {
    var plane: Plane? = null
    override var width: Int
        get() = plane?.width?.toInt() ?: 0
        set(value) { plane?.width = value.toFloat() }
    override var height: Int
        get() = plane?.height?.toInt() ?: 0
        set(value) {plane?.height = value.toFloat()}


    var colorFuncID: ColorType = ColorType.Zero
    val actionStack = ActionStack(this)
    private var img = BufferedImage(
        1,
        1,
        BufferedImage.TYPE_INT_ARGB,
    )
    var refresh = true
    var dinamycIters = false
    var s = 6.0
    override fun paint(scope: DrawScope) {
        if (refresh) {
            refresh = false
            img = BufferedImage(
                scope.size.width.toInt(),
                scope.size.height.toInt(),
                BufferedImage.TYPE_INT_ARGB,
            )
            getImageFromPlane(img)
        }
        scope.drawImage(img.toComposeImageBitmap())
    }

    fun retDynIt() {
        getImageFromPlane(img)
    }

    fun getImageFromPlane(img:BufferedImage): BufferedImage{
        if (dinamycIters) Mandelbrot.maxIterations = 200 * otherIters(s).toInt()
        else Mandelbrot.maxIterations = 200
        plane?.let { plane ->
            val tc = Runtime.getRuntime().availableProcessors()
            List(tc) { t ->
                thread {
                    for (i in t..<width step tc)
                        for (j in 0..<height) {
                            val x = Complex(
                                Converter.xScr2Crt(i.toFloat(), plane),
                                Converter.yScr2Crt(j.toFloat(), plane)
                            )
                            val colorFunk = colorFunc(colorFuncID.value)
                            img.setRGB(i, j, colorFunk(fractal.isInSet(x)).toArgb())
                        }
                }
            }.forEach { it.join() }
        }
        return img
    }
    fun otherIters(s: Double): Double {
        plane?.let{ plane ->
            this.s = 0.1 / ((plane.xMax - plane.xMin) * (plane.yMax - plane.yMin))
        }
        return s
    }
}

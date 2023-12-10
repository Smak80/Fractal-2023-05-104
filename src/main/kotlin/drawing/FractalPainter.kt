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

    var xMax: Double
        get() = plane?.xMax ?: 0.0
        set(value) {plane?.xMax = value}
    var xMin: Double
        get() = plane?.xMin ?: 0.0
        set(value) {plane?.xMin = value}
    var yMax: Double
        get() = plane?.yMax ?: 0.0
        set(value) {plane?.yMax = value}
    var yMin: Double
        get() = plane?.yMin ?: 0.0
        set(value) {plane?.yMin = value}

    val dwh: Double
        get() = width * 1.0 / height

    fun proportions(){
        val xlen = Math.abs(xMax - xMin)
        val ylen = Math.abs(yMax - yMin)
        if(Math.abs(xlen/ylen - dwh) > 1E-6){
            if(xlen/ylen < dwh){
                val dx = ylen * dwh - xlen
                xMax += dx/2
                xMin -= dx/2
            }
            if(xlen/ylen > dwh){
                val dy = xlen / dwh - ylen
                yMax += dy/2
                yMin -= dy/2
            }
        }
    }


    override fun paint(scope: DrawScope) {
        this.proportions()
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

    fun getImageFromPlane(img:BufferedImage): BufferedImage{
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
        return  img
    }
}
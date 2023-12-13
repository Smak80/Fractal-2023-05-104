package video

import drawing.convertation.ColorFuncs
import drawing.convertation.Plane
import java.awt.image.BufferedImage

object VideoMakerHelper{
    fun createIntermediateFrames(frame1: Plane, frame2: Plane, numFrames: Int,width:Int,height:Int): List<BufferedImage> {
        val intermediateFrames = ArrayList<BufferedImage>()

        for (i in 0..numFrames) {
            val alpha = i.toDouble() / numFrames
            val blendedFrame = blendFrames(frame1, frame2, alpha,width,height)
            intermediateFrames.add(blendedFrame)
        }

        return intermediateFrames
    }

    private fun blendFrames(frame1: Plane, frame2: Plane, alpha: Double, width: Int, height: Int): BufferedImage {
        val xMin = interpolate(frame1.xMin, frame2.xMin, alpha)
        val yMin = interpolate(frame1.yMin, frame2.yMin, alpha)
        val xMax = interpolate(frame1.xMax, frame2.xMax, alpha)
        val yMax = interpolate(frame1.yMax, frame2.yMax, alpha)
        val plane = Plane(xMin, xMax, yMin, yMax, 0f, 0f)

        return Cadre.getImageFromPlane(plane, width.toFloat(), height.toFloat(), ColorFuncs.First)
    }

    private fun interpolate(value1: Double, value2: Double, alpha: Double): Double {
        return value1 * (1 - alpha) + value2 * alpha
    }
}
package video

import drawing.convertation.ColorFuncs
import drawing.convertation.Plane
import math.CatmullRomInterpolation
import math.Complex
import org.jcodec.api.awt.AWTSequenceEncoder
import org.jcodec.common.io.NIOUtils
import org.jcodec.common.io.SeekableByteChannel
import org.jcodec.common.model.Rational
import java.awt.image.BufferedImage
import java.lang.Exception
import java.util.stream.Collectors
import kotlin.math.*


@Suppress("NAME_SHADOWING")
class VideoMaker(private val conf: VideoConfiguration) {
    enum class InterpolationMethod {
        CatmullRom,
        //CatmullRom
    }

    var rendered = 0
    var toRenderAndCreate = conf.duration * conf.fps * 2

    fun getVideo(method: InterpolationMethod) {
        val images = when (method) {
           // InterpolationMethod.CatmullRom -> getCadresCatmullRom()
            InterpolationMethod.CatmullRom -> getCadresLinear()
        }
        render(images)
    }

    private fun getCadresLinear(): List<BufferedImage> {
        var cadres = conf.cadres
        val cadresList: MutableList<BufferedImage> = mutableListOf()
        val framesPerSegment = (conf.duration * conf.fps) / (conf.cadres.size - 1)
        //val intermediateFrames = VideoMakerHelperSmooth.createIntermediateFrames(cadres, conf.duration * conf.fps,conf.width.toInt(),conf.height.toInt())
        for (i in 0 until conf.cadres.size - 1) {
            val intermediateFrames = VideoMakerHelper.createIntermediateFrames(cadres[i].plane, cadres[i+1].plane, framesPerSegment,conf.width.toInt(),conf.height.toInt())
            cadresList.addAll(intermediateFrames)
        }
        return cadresList
    }
    fun createIntermediateFrames(frame1: Plane, frame2: Plane, numFrames: Int,width:Int,height:Int): List<BufferedImage> {
        val intermediateFrames = ArrayList<BufferedImage>()

        for (i in 0..numFrames) {
            val alpha = i.toDouble() / numFrames
            val blendedFrame = blendFrames(frame1, frame2, alpha)
            intermediateFrames.add(blendedFrame)
            rendered+=1
        }

        return intermediateFrames
    }

    private fun blendFrames(frame1: Plane, frame2: Plane, alpha: Double): BufferedImage {
        val xMin = interpolate(frame1.xMin, frame2.xMin, alpha)
        val yMin = interpolate(frame1.yMin, frame2.yMin, alpha)
        val xMax = interpolate(frame1.xMax, frame2.xMax, alpha)
        val yMax = interpolate(frame1.yMax, frame2.yMax, alpha)
        val plane = Plane(xMin, xMax, yMin, yMax, 0f, 0f)

        return Cadre.getImageFromPlane(plane, conf.width.toFloat(), conf.height.toFloat(), ColorFuncs.First)
    }

    private fun interpolate(value1: Double, value2: Double, alpha: Double): Double {
        return value1 * (1 - alpha) + value2 * alpha
    }
    private fun render(data: List<BufferedImage>) {
        val out: SeekableByteChannel? = null
        try {
            val out = NIOUtils.writableFileChannel(conf.file);
            println(out)
            val encoder = AWTSequenceEncoder(out, Rational.R(conf.fps, 1))

            data.forEach {
                println("Идём")
                encoder.encodeImage(it)
                rendered += 1
            }
            encoder.finish();
        } finally {
            NIOUtils.closeQuietly(out);
        }
    }
}


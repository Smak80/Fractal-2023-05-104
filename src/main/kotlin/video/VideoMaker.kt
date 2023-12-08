package video

import org.jcodec.api.awt.AWTSequenceEncoder
import org.jcodec.common.io.NIOUtils
import org.jcodec.common.io.SeekableByteChannel
import org.jcodec.common.model.Rational
import java.awt.image.BufferedImage


@Suppress("NAME_SHADOWING")
class VideoMaker(private val conf: VideoConfiguration) {
    fun getVideo() {
        val images = getData()
        render(images)
    }

    private fun getData(): List<BufferedImage> {
        val cadresList: MutableList<BufferedImage> = mutableListOf()
        val framesPerSegment = (conf.duration * conf.fps) / (conf.cadres.size - 1)
        for (i in 0 until conf.cadres.size - 1) {
            val currCadrePlane = conf.cadres[i].plane
            var currPlane = drawing.convertation.Plane(
                currCadrePlane.xMin,
                currCadrePlane.xMax,
                currCadrePlane.yMin,
                currCadrePlane.yMax,
                conf.width,
                conf.height
            )
            val stepDxMax = (conf.cadres[i + 1].plane.xMax - conf.cadres[i].plane.xMax) / framesPerSegment
            val stepDxMin = (conf.cadres[i + 1].plane.xMin - conf.cadres[i].plane.xMin) / framesPerSegment
            val stepDyMax = (conf.cadres[i + 1].plane.yMax - conf.cadres[i].plane.yMax) / framesPerSegment
            val stepDyMin = (conf.cadres[i + 1].plane.yMin - conf.cadres[i].plane.yMin) / framesPerSegment
            for (j in 0 until framesPerSegment) {
                val bi = Cadre.getImageFromPlane(currPlane, conf.width, conf.height)
                cadresList.add(bi)
                currPlane = drawing.convertation.Plane(
                    currPlane.xMin + stepDxMin,
                    currPlane.xMax + stepDxMax,
                    currPlane.yMin + stepDyMin,
                    currPlane.yMax + stepDyMax,
                    currPlane.width, currPlane.height
                )
            }
        }
        return cadresList
    }

    private fun render(data: List<BufferedImage>) {
        val out: SeekableByteChannel? = null
        try {
            val out = NIOUtils.writableFileChannel(conf.file);
            println(out)
            val encoder = AWTSequenceEncoder(out, Rational.R(conf.fps, 1))
            data.forEach{
                println("Идём")
                encoder.encodeImage(it)
            }
            encoder.finish();
        }
        finally {
            NIOUtils.closeQuietly(out);
        }
    }
}
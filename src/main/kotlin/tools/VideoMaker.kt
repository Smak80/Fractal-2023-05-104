package tools

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import org.jcodec.api.SequenceEncoder
import org.jcodec.common.io.NIOUtils
import org.jcodec.common.model.ColorSpace
import org.jcodec.common.model.Rational
import java.awt.image.BufferedImage
import java.io.File

class VideoMaker(var height: Int,
                 var width: Int,
                 var fps: Int,
                 var duration: Int,
                 var imageList: SnapshotStateList<BufferedImage>,
                 var filename: String
)
{
    private fun createVideo(
        height: Int,
        width: Int ,
        fps: Int ,
        duration: Int,
        imageList: SnapshotStateList<BufferedImage>,
    ){
        this.height = height
        this.width = width
        this.fps = fps
        this.duration = duration
        this.imageList = imageList
    }

    private fun create(data: List<BufferedImage>) {
        val output = File(filename)

        val enc = SequenceEncoder.createWithFps(NIOUtils.writableChannel(output), Rational(fps, 1))
        for (img in data) {

        }
    }
}
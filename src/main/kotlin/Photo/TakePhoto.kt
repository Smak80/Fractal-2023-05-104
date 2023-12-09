package Photo

import drawing.FractalPainter
import java.awt.AlphaComposite
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun TakePhoto(fp: FractalPainter) {
    val file = File("screenshot2.png")
    ImageIO.write(fp.img, "png", file)
    val bufferedImage: BufferedImage = ImageIO.read(File("screenshot2.png"));
    val newBufferedImage = BufferedImage(
        bufferedImage.width,
        bufferedImage.height + 20, BufferedImage.TYPE_INT_RGB
    )
    newBufferedImage.createGraphics().drawImage(fp.img, 0, 0, java.awt.Color.WHITE, null);
    newBufferedImage.createGraphics().composite = AlphaComposite.SrcOut
    newBufferedImage.createGraphics().drawString(
        "xMin = ${fp.plane?.xMin}, " +
                "xMax = ${fp.plane?.xMax}, " +
                "yMin = ${fp.plane?.yMin}, " +
                "yMax = ${fp.plane?.yMax}",
        10, newBufferedImage.height - 2
    )
    ImageIO.write(newBufferedImage, "jpg", File("screen.jpg"));
    file.delete()
}

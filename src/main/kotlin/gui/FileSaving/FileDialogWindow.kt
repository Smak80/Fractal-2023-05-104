package gui.FileSaving

import androidx.compose.foundation.Image
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.filechooser.FileNameExtensionFilter


fun FileDialogWindow(Photo: BufferedImage) {
    val fileDialogFrame = JFrame()
    val fileChooser = JFileChooser()

    val filter = FileNameExtensionFilter(
        "Фотографии", "png", "jpg"
    )

    fileChooser.fileFilter = filter
    fileChooser.dialogTitle = "Сохранение фрактала"
    fileChooser.fileSelectionMode = JFileChooser.FILES_ONLY;
    val returnVAl = fileChooser.showSaveDialog(fileDialogFrame)
    if (returnVAl == JFileChooser.APPROVE_OPTION ) {
        JOptionPane.showMessageDialog(
            fileDialogFrame,
            "Файл '" + fileChooser.getSelectedFile() +
                    " ) сохранен"
        );
        ImageIO.write(Photo, "jpg", File("${fileChooser.getSelectedFile()}.jpg"))
    }




}
package tools

import drawing.FractalPainter
import math.fractals.FractalData
import java.awt.Font
import java.awt.image.BufferedImage
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import javax.imageio.ImageIO
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.filechooser.FileSystemView


object FileManager {
    fun saveFractalData(data: FractalData){
        val fileSystemView = FileSystemView.getFileSystemView()
        val fileChooser = JFileChooser(fileSystemView.defaultDirectory, fileSystemView).apply {
            dialogTitle = "Сохранение состояния фрактала"
            fileFilter = FileNameExtensionFilter(".fractal", "fractal")
            isAcceptAllFileFilterUsed = false
            fileSelectionMode = JFileChooser.OPEN_DIALOG
        }
        val openDialogResult = fileChooser.showSaveDialog(fileChooser)
        if (openDialogResult == JFileChooser.APPROVE_OPTION) {
            val fileAbsolutePath =
                fileChooser.currentDirectory.absolutePath + "\\" +
                        fileChooser.selectedFile.nameWithoutExtension + ".fractal"
            val fileStream = FileOutputStream(fileAbsolutePath)
            val objectStream = ObjectOutputStream(fileStream)
            objectStream.writeObject(data)
            objectStream.close()
            JOptionPane.showMessageDialog(fileChooser, "Файл '$fileAbsolutePath' успешно сохранен")
        }
    }

    fun loadFractalData() :  FractalData? {
        val fileChooser = JFileChooser().apply {
            dialogTitle = "Открытие фрактала"
            fileFilter = FileNameExtensionFilter(".fractal", "fractal")
            isAcceptAllFileFilterUsed = false
            fileSelectionMode = JFileChooser.OPEN_DIALOG
        }
        val openDialogResult = fileChooser.showOpenDialog(fileChooser)
        if (openDialogResult == JFileChooser.APPROVE_OPTION) {
            val fileAbsolutePath = fileChooser.selectedFile.absolutePath
            val fileStream = FileInputStream(fileAbsolutePath)
            val objectStream = ObjectInputStream(fileStream)
            val result = objectStream.readObject() as? FractalData
            return if ( result is FractalData){
                JOptionPane.showMessageDialog(fileChooser, "\"Файл '$fileAbsolutePath' успешно открыт\"")
                result
            } else{
                JOptionPane.showMessageDialog(fileChooser, "Error")
                null
            }
        }
        return null
    }

    fun getPathForVideoSave(): String?{
        val fileSystemView = FileSystemView.getFileSystemView()
        val fileChooser = JFileChooser(fileSystemView.defaultDirectory, fileSystemView).apply {
            dialogTitle = "Сохранить Видео"
            fileFilter = FileNameExtensionFilter(".mp4", "mp4")
            isAcceptAllFileFilterUsed = false
            fileSelectionMode = JFileChooser.OPEN_DIALOG
        }
        val openDialogResult = fileChooser.showSaveDialog(fileChooser)
        if (openDialogResult == JFileChooser.APPROVE_OPTION) {
            return fileChooser.currentDirectory.absolutePath + "\\" +
                    fileChooser.selectedFile.nameWithoutExtension + ".mp4"
        }
        return null

    }

    fun saveImageData(fp: FractalPainter){
        val fileSystemView = FileSystemView.getFileSystemView()
        val fileChooser = JFileChooser(fileSystemView.defaultDirectory, fileSystemView).apply {
            dialogTitle = "Сохранить изображение"
            fileFilter = FileNameExtensionFilter("Изображение", "png")
            isAcceptAllFileFilterUsed = false
            fileSelectionMode = JFileChooser.OPEN_DIALOG
        }
        val openDialogResult = fileChooser.showSaveDialog(fileChooser)
        if (openDialogResult == JFileChooser.APPROVE_OPTION) {
            val fileAbsolutePath =
                fileChooser.currentDirectory.absolutePath + "\\" +
                        fileChooser.selectedFile.nameWithoutExtension + ".png"
            val image = addCartCoordinates(fp)
            val fileStream = FileOutputStream(fileAbsolutePath)
            ImageIO.write(image,"png",fileStream)
            JOptionPane.showMessageDialog(fileChooser, "Изображение '$fileAbsolutePath' успешно сохранен")
        }
    }
    private fun addCartCoordinates(fp:FractalPainter): BufferedImage {
        val image = fp.img
        val graphics = image.createGraphics()
        val font = Font("Futura", Font.BOLD, 16)
        graphics.font = font
        graphics.color = java.awt.Color(0,0,0)

        fp.plane?.let{
            val string = "xMin=${it.xMin} xMax=${it.xMax} yMin=${it.yMin} yMax=${it.yMax}"
            val fm = graphics.fontMetrics
            val stringWidth = fm.stringWidth(string)
            val stringHeight = fm.height
            val x = (image.width - stringWidth) / 2
            val y = image.height - stringHeight - 10


            graphics.color = java.awt.Color(255, 255, 255)
            graphics.fillRoundRect(x - 5, y - fm.ascent - 5, stringWidth + 10, stringHeight + 10,30,30)


            graphics.color = java.awt.Color(0,0,0)
            graphics.drawString(string, x, y)
            graphics.dispose()
        }
        return image
    }

//    private fun experiment(){
//        val a = DrawScope
//        val text = it.measure(
//            df.format(value),
//            TextStyle(color = LABELS_COLOR, fontSize = FONT_SIZE, fontWeight = FONT_WEIGHT)
//        )
//        plane?.let { plane ->
//            var y = Converter.yCrt2Scr(0.0, plane).coerceIn(0f, plane.height)
//            if(y==plane.height) y -= 34f  else y += 17f
//
//            val x = Converter.xCrt2Scr(value, plane) - text.size.width / 2
//            drawText(text, topLeft = Offset(x, y))
//        }
//    }
}
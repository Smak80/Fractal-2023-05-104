package tools

import drawing.convertation.Plane
import drawing.convertation.colorFunc
import math.fractals.FractalData
import org.jetbrains.skia.Color
import video.Cadre
import java.awt.Font
import java.awt.FontMetrics
import java.awt.font.LineMetrics
import java.awt.font.TextAttribute
import java.awt.image.BufferedImage
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.text.AttributedString
import javax.imageio.ImageIO
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.filechooser.FileSystemView
import java.awt.Graphics2D





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

    fun saveImageData(data: FractalData){
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
            val image = addCartCoordinates(data)
            val fileStream = FileOutputStream(fileAbsolutePath)
            ImageIO.write(image,"png",fileStream)
            JOptionPane.showMessageDialog(fileChooser, "Изображение '$fileAbsolutePath' успешно сохранен")
        }
    }
    private fun addCartCoordinates(data:FractalData):BufferedImage{
        val plane = Plane(data.xMin,data.xMax,data.yMax,data.yMin,1920f,1080f)
        val a = Cadre.getImageFromPlane(plane,1920f,1080f, colorFunc(data.colorscheme))
        a.graphics.also {
            val string = "xMin=${plane.xMin} xMax = ${plane.xMax} yMin = ${plane.yMin}, yMax = ${plane.yMax}"
            val text = AttributedString(string)
            text.addAttribute(TextAttribute.FONT, Font("Helvetica", Font.BOLD, 24), 0, string.length)

//            it.color = java.awt.Color.WHITE
//            it.fillRoundRect(20, 10, 550 , it.fontMetrics.ascent + it.fontMetrics.height + 10, 30, 30)
            it.color = java.awt.Color.BLACK
            it.drawString(text.iterator, 40, 40)
        }
        return a
    }
}
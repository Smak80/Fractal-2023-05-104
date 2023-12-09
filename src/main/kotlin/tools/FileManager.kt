package tools

import drawing.convertation.Plane
import drawing.convertation.colorFunc
import math.fractals.FractalData
import org.jetbrains.skia.Color
import video.Cadre
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

    fun saveImageData(data: FractalData){
        val fileSystemView = FileSystemView.getFileSystemView()
        val fileChooser = JFileChooser(fileSystemView.defaultDirectory, fileSystemView).apply {
            dialogTitle = "Сохранить изображение"
            fileFilter = FileNameExtensionFilter("Изображение", "jpeg")
            isAcceptAllFileFilterUsed = false
            fileSelectionMode = JFileChooser.OPEN_DIALOG
        }
        val openDialogResult = fileChooser.showSaveDialog(fileChooser)
        if (openDialogResult == JFileChooser.APPROVE_OPTION) {
            val fileAbsolutePath =
                fileChooser.currentDirectory.absolutePath + "\\" +
                        fileChooser.selectedFile.nameWithoutExtension + ".jpeg"
            val image = prepareImg(data)
            val fileStream = FileOutputStream(fileAbsolutePath)
            ImageIO.write(image,"jpeg",fileStream)
            JOptionPane.showMessageDialog(fileChooser, "Изображение '$fileAbsolutePath' успешно сохранен")
        }
    }

    private fun prepareImg(data:FractalData):BufferedImage{
        val plane = Plane(data.xMax,data.xMin,data.yMax,data.yMin,1920f,1080f)
        val a = Cadre.getImageFromPlane(plane,1920f,1080f, colorFunc(data.colorscheme))
        a.graphics.also {
            it.color = java.awt.Color(0, 0, 0)
            val string = "xMin=${plane.xMin} xMax = ${plane.xMax} yMin = ${plane.yMin}, yMax = ${plane.yMax}"
            it.drawString(string,30, 30)
        }
        return a
    }
}
package tools

import math.fractals.FractalData
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.filechooser.FileSystemView

object FractalDataProcessor {

    object FractalDataProcessor {
        fun saveFractal(data: FractalData){
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

        fun loadData() :  FractalData? {
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
                    result
                } else{
                    JOptionPane.showMessageDialog(fileChooser, "Error")
                    null
                }
            }
            return null
        }
    }
}
package guiforfractal.fileDialogWindow

import drawing.FractalPainter
import drawing.colors.colors
import math.fractals.funcs
import java.io.File
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.WindowConstants
import javax.swing.filechooser.FileNameExtensionFilter


fun fileOpeningDialogWindow(fp: FractalPainter){
    val fileDialogFrame = JFrame()
    fileDialogFrame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    val fileChooser = JFileChooser()

    val filter = FileNameExtensionFilter(
        "Фрактал в формате txt", "txt"
    )

    fileChooser.fileFilter = filter
    fileChooser.dialogTitle = "Открыть фрактал в собственном формате"
    fileChooser.fileSelectionMode = JFileChooser.FILES_ONLY

    fileChooser.showOpenDialog(fileDialogFrame)

    try {
        if (fileChooser.selectedFile != null) {
            val filePath = fileChooser.selectedFile.path
            val file = File(filePath)
            val fractalParams = file.readText()

            val ableFunctions = funcs.keys


            val maxIterations = """^\d+""".toRegex()
            val planePattern = """Plane\(xMin=(-?\d+\.\d+), xMax=(-?\d+\.\d+), yMin=(-?\d+\.\d+), yMax=(-?\d+\.\d+), width=(\d+\.\d+), height=(\d+\.\d+)\)""".toRegex()
            val colorPattern = """(color\d+)""".toRegex()
            val functionPattern = ableFunctions.joinToString("|").toRegex()


            val maxIterationsResult = maxIterations.find(fractalParams)
            val planeMatch = planePattern.find(fractalParams)
            val colorMatch = colorPattern.find(fractalParams)
            val functionResult = functionPattern.find(fractalParams)


            if (maxIterationsResult != null) {
                fp.fractal.maxIterations = maxIterationsResult.value.toInt()
                println("maxIterations: ${maxIterationsResult.value}")
            } else {
                println("В начале строки нет положительного целого числа")
            }

            if (planeMatch != null) {
                val (xMin, xMax, yMin, yMax, width, height) = planeMatch.destructured
                fp.plane?.let {
                    it.xMin = xMin.toDouble()
                    it.xMax = xMax.toDouble()
                    it.yMin = yMin.toDouble()
                    it.yMax = yMax.toDouble()
                    it.width = width.toFloat()
                    it.height = height.toFloat()
                }
                println("Plane: xMin=$xMin, xMax=$xMax, yMin=$yMin, yMax=$yMax, width=$width, height=$height")
            } else {
                println("No plane match found")
            }

            if (colorMatch != null) {
                val (colorNumber) = colorMatch.destructured
                fp.colorFunc = colors[colorNumber]!!
                println("Color: $colorNumber")
            } else {
                println("No color match found")
            }

            if (functionResult != null) {
                fp.FRACTAL.function = funcs[functionResult.value]!!
                println("Function: ${functionResult.value}")
            } else {
                println("No color match found")
            }

            fp.refresh = true
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
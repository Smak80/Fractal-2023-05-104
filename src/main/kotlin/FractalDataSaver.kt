import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.window.DialogState
import java.awt.FileDialog
import java.io.*
import java.util.*
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.filechooser.FileNameExtensionFilter

object FractalDataFileSaver{
    fun saveFractalDataToFile(fractalData: FractalData, filePath: String) {
        try {
            val fileOutputStream = FileOutputStream("$filePath.fractal")
            val objectOutputStream = ObjectOutputStream(fileOutputStream)

            objectOutputStream.writeObject(fractalData)

            objectOutputStream.close()
            fileOutputStream.close()

            println("FractalData saved successfully to $filePath.fractal")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
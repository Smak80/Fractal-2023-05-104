import androidx.compose.runtime.remember
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.window.DialogState
import java.awt.FileDialog
import java.io.File
import java.io.FileOutputStream
import java.io.FilenameFilter
import java.io.ObjectOutputStream
import java.util.*
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.filechooser.FileNameExtensionFilter

object FractalDataFileSaver{

    val sd = remember {
        FileDialog(ComposeWindow(), "Сохранить фрактал", FileDialog.SAVE)
    }.also { dialog ->
        dialog.setFilenameFilter(FilenameFilter { _, filename ->
            val extension = File(filename).extension.lowercase(Locale.getDefault())
            val allowedExtensions = setOf("fractal")
            allowedExtensions.contains(extension)
        })
    }
    fun saveFile(data: FractalData) {
        if (sd.isVisible) {
            val selectedFile = sd.file
            val filePath = sd.directory + selectedFile

            // Сериализация объекта FractalData и сохранение в файл
            val file = File(filePath)
            ObjectOutputStream(FileOutputStream(file)).use { objectOutputStream ->
                objectOutputStream.writeObject(data)
            }
        }
    }
}
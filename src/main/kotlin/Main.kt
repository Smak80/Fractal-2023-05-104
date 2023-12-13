import androidx.compose.ui.input.key.Key.Companion.Window
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import java.awt.Dimension
import javax.swing.UIManager

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "FractaLAB 2.0",
        state = rememberWindowState(
            width = 800.dp,
            height = 600.dp,
            placement = WindowPlacement.Floating,
            position = WindowPosition(100.dp, 100.dp),
            isMinimized = false
        ),
    ) {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        this.window.minimumSize = Dimension(800, 600)
        App()
    }
}

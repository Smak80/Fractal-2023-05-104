import androidx.compose.ui.input.key.Key.Companion.Window
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import java.awt.Dimension

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Множество Мандельброта",
        state = rememberWindowState(
            width = 800.dp,
            height = 600.dp,
            placement = WindowPlacement.Floating,
            position = WindowPosition(100.dp, 100.dp),
            isMinimized = false
        ),
    ) {
        this.window.minimumSize = Dimension(800, 600)
        App()
    }
}

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import controls.*
import drawing.FractalPainter
import drawing.convertation.Plane
import math.fractals.FractalData
import math.fractals.Mandelbrot
import org.jetbrains.skiko.loadBytesFromPath
import tools.FractalDataProcessor
import java.awt.FileDialog
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.log2
import kotlin.math.sin

@Composable
@Preview
fun App(){
    val fileDialogSaver = remember {  FileDialog(ComposeWindow(), "Сохранить фрактал", FileDialog.SAVE).apply {
        isMultipleMode = false
        setFilenameFilter { _, filename ->
            val extension = File(filename).extension.lowercase(Locale.getDefault())
            extension == "fractal"
        }
    }}
    val fileDialogLoader = remember {  FileDialog(ComposeWindow(), "Открыть фрактал", FileDialog.LOAD).apply {
        isMultipleMode = false
        setFilenameFilter { _, filename ->
            val extension = File(filename).extension.lowercase(Locale.getDefault())
            extension == "fractal"
        }
    }}
    val fp = remember { FractalPainter(Mandelbrot){
        if (it == 1f) Color.Black
        else {
            val r = sin(it*15f).absoluteValue
            val g = (sin(-8f*it)* cos(it*5f+12f)).absoluteValue
            val b = log2(2f - cos(sin(18*-it)))
            Color(r, g, b)
        }
    } }

    fp.plane = Plane(-2.0, 1.0, -1.0, 1.0, 0f, 0f)
    MaterialTheme{
        Scaffold(
            topBar = {
                var dynamicIterationsCheck by remember { mutableStateOf(false) }
                var isMenuExpanded by remember { mutableStateOf(false) }
                val list = remember { SnapshotStateList<BufferedImage>() }

                TopAppBar(
                    title = {
                        Text(
                            modifier = Modifier,
                            textAlign = TextAlign.Center,
                            text = "Множество Мондельброта"
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { isMenuExpanded = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Меню")
                            if (isMenuExpanded) {
                                // Выпадающий список
                                DropdownMenu(
                                    expanded = isMenuExpanded,
                                    onDismissRequest = { isMenuExpanded = false }
                                ) {
                                    showSaveDialog("Cохранить",
                                        {
                                            TODO("Реализовать функцию для сохранения изображения")
                                            isMenuExpanded = false
                                        }, {
                                            fileDialogSaver.isVisible = true
                                            val selectedFile = fileDialogSaver.file
                                            val filePath = fileDialogSaver.directory + selectedFile
                                            fp.plane?.let{
                                                val fractalData = FractalData(it.xMin,it.xMax,it.yMin,it.yMax, 1)
                                                FractalDataProcessor.saveFractalDataToFile(fractalData,filePath)
                                            }
                                            isMenuExpanded  = false
                                        },{
                                            isMenuExpanded = false
                                        })
                                    DropdownMenuItem(
                                        onClick = {
                                            fileDialogLoader.isVisible = true
                                            val selectedFile = fileDialogLoader.file
                                            val filePath = fileDialogLoader.directory + selectedFile
                                            val resData = FractalDataProcessor.readFractalDataFromFile(filePath)
                                            println(resData?.xMax ?: "null")
                                            resData?.let { fd ->
                                                fp.plane?.let { plane ->
                                                    fp.plane = Plane(fd.xMin, fd.xMax, fd.yMin, fd.yMax, plane.width, plane.height)
                                                    fp.refresh = true
                                                }
                                            }
                                            isMenuExpanded = false
                                        }
                                    ){
                                        Text("Открыть")
                                    }
                                }
                            }
                        }
                    },
                    actions = {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 2.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        )
                        {
                            //Кнопка Назад
                            IconButton(
                                onClick = {TODO("Отмена действий!")}
                            ) {
                                Icon(
                                    Icons.Default.ArrowBack,
                                    "Назад"
                                )
                            }
                            //Для Вызова Окна с Видео
                            Row(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colors.secondaryVariant,
                                        shape = RoundedCornerShape(50.dp)
                                    )
                            ) {
                                var showVideoDialogBoolean by remember { mutableStateOf(false) }
                                Button(
                                    modifier = Modifier.padding(start = 16.dp),
                                    onClick = { showVideoDialogBoolean = true },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
                                ) {
                                    Text("Создать Видео")
                                    if (showVideoDialogBoolean) {
                                        // Диалоговое окно
                                        Dialog(
                                            onDismissRequest = { showVideoDialogBoolean = false },
                                            properties = DialogProperties(dismissOnClickOutside = true)
                                        ) {
                                            workWithVideoDialog(list) { showVideoDialogBoolean = false }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.width(5.dp))
                                IconButton(
                                    onClick = {
                                        TODO("Тут придумай")
                                        if(list.size > 0){
                                            println(list[0].width)
                                            println(list[0].height)
                                        }
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Add,
                                        "Добавить Кадр"
                                    )
                                }
                            }
                            //Выбор Цветовой Схемы
                            dropdownMenuIcon(
                                mapOf(),
                            )
                            // Checkbox для динамических итераций
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Checkbox(
                                    checked = dynamicIterationsCheck,
                                    onCheckedChange = {dynamicIterationsCheck = it},
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                                Text(
                                    text = "D. итерации",
                                    style = MaterialTheme.typography.body1.copy(
                                        fontSize = 18.sp,
                                        color = Color.White
                                    )
                                )
                            }
                        }
                    },
                    modifier = Modifier.height(65.dp)
                )
            },
            modifier = Modifier.fillMaxSize()){
            Box(
                Modifier.fillMaxSize()
            ){
                mainFractalWindow(fp)
            }
        }
    }
}

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
        App()
    }
}

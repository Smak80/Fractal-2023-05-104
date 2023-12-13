package gui.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import drawing.convertation.ColorFuncs
import gui.controls.intTextField
import tools.FileManager
import video.Cadre
import video.VideoConfiguration
import video.VideoMaker


@Composable
fun workWithVideoDialog(
    colorScheme: ColorFuncs,
    imageList: SnapshotStateList<Cadre>,
    close:()->Unit,
) {
    var height = 600
    var width = 800
    var fps = 24
    var duration = 5
    var vm: VideoMaker

    var currentProgress by remember { mutableStateOf(0f) }
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(MaterialTheme.colors.surface, shape = MaterialTheme.shapes.medium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Текст в диалоговом окне
            Text(
                "Параметры Видео",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
            )
            //Закрытие
            IconButton(
                onClick = close,
                modifier = Modifier
                    .padding(horizontal = 8.dp),
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Закрыть",
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            intTextField(
                value = width,
                label = "Ширина",
                onValueChange = {width=it},
                maxValue = 10000,
                modifier = Modifier.weight(1f)
            )
            intTextField(
                value = height,
                label = "Высота",
                onValueChange = {height=it},
                maxValue = 10000,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            intTextField(
                value = fps,
                label = "FPS",
                onValueChange = {fps=it},
                minValue = 1,
                maxValue = 240,
                modifier = Modifier.weight(1f)
            )
            intTextField(
                value = duration,
                label = "Длительность, c",
                onValueChange = {duration=it},
                maxValue = 10*60*60,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    val filePath = FileManager.getPathForVideoSave()?.let {
                        val configuration = VideoConfiguration(
                            width.toFloat(),
                            height.toFloat(),
                            duration,
                            fps,
                            it,
                            imageList,
                            colorScheme
                        )
                        vm = VideoMaker(configuration)
                        println("Начинаем делать видео")
                        vm.getVideo(VideoMaker.InterpolationMethod.CatmullRom)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .height(45.dp)
            ) {
                Text("Создать")
            }
            Button(
                onClick = { imageList.clear() },
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .height(45.dp)
            ) {
                Text("Очистить")
            }
        }
        if (loading) {
            LinearProgressIndicator(
                strokeCap = StrokeCap.Round,
                modifier = Modifier.fillMaxWidth(),
                progress = currentProgress ,
            )
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(imageList) {
                myCard(it.preRenderImg) { imageList.remove(it) }
            }
        }
    }
}


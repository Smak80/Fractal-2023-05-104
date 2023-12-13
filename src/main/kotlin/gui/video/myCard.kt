package gui.video

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.graphics.withSave
import androidx.compose.ui.unit.dp
import java.awt.image.BufferedImage


@Composable
fun myCard(bufferedImage: BufferedImage, onDel:()->Unit) {
    val sample = bufferedImage.toComposeImageBitmap()
    Card(
        modifier = Modifier
            .background(color = Color.Magenta)
            .width(110.dp)
            .height(110.dp),
        backgroundColor = MaterialTheme.colors.primarySurface
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Canvas(
                modifier = Modifier
            ) {
                drawIntoCanvas { canvas ->
                    canvas.withSave {
                        canvas.drawImage(sample, Offset.Zero, Paint())
                        canvas.translate(sample.width.toFloat(), 0f)
                    }
                }
            }
            IconButton(
                onClick = { onDel() },
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    Icons.Default.Close,
                    "Удалить"
                )
            }
        }
    }
}
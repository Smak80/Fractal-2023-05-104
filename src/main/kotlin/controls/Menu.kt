package controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.awt.image.BufferedImage

@Composable
fun menu(
    saveImage:()->Unit,
    saveFractal:()->Unit,
    openFractal: ()->Unit,
    back: ()->Unit,
    showVideoDialog: ()->Unit,
    addFrames: BufferedImage,
    themesMap: Map<String,()->Unit>,
    dynamicIterationsCheck: Boolean,
    dynamicIterationsCheckChange: (Boolean)->Unit

){

}
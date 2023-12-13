package drawing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

class SelectionRect(p: Offset) {
    private var pt1 by mutableStateOf<Offset>(p)
    private var sz by mutableStateOf(Size.Zero)
    val topLeft: Offset
        get() = Offset(
            min(pt1.x, pt1.x + sz.width),
            min(pt1.y, pt1.y + sz.height)
        )
    val size: Size
        get() = Size(
            sz.width.absoluteValue,
            sz.height.absoluteValue
        )

    fun addPoint(p:Offset) { sz = Size(sz.width + p.x, sz.height + p.y) }
}
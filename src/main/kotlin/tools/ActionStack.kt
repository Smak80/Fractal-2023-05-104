package tools

import drawing.FractalPainter
import drawing.convertation.ColorFuncs
import math.fractals.FractalData
import java.util.*

class ActionStack(private val fp: FractalPainter){
    private val stack = Stack<Any>()
    fun push(obj: Any) = stack.push(obj)
    fun pop() {
        if (stack.isEmpty()) return
        when (val obj: Any = stack.pop()) {
            is CartCords -> {
                fp.plane?.let{
                    it.xMin = obj.xMin
                    it.xMax = obj.xMax
                    it.yMin = obj.yMin
                    it.yMax = obj.yMax
                }
                fp.xMin = obj.xMin
                fp.xMax = obj.xMax
                fp.yMin = obj.yMin
                fp.yMax = obj.yMax
                fp.refresh = true
            }
            is ColorFuncs -> {
                fp.colorFuncID = obj
                fp.refresh = true
            }
            is FractalData -> {
                fp.colorFuncID = obj.colorscheme
            }
            else -> throw Exception("В стек попал объект неизвестного типа")
        }

    }
    fun copy(): ActionStack {
        val a = ActionStack(fp)
        a.stack.addAll(stack)
        return a
    }
    data class CartCords(
        val xMin:Double,
        val xMax:Double,
        val yMin:Double,
        val yMax:Double,
    )

}


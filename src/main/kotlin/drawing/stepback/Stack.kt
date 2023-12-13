package drawing.stepback

import java.util.Stack

class Stack {
    private var stack = Stack<Any?>()
    fun push(elem: Any?){
        stack.push(elem)
    }
    fun pop(elem: Any?): Any? {
        val top: Any?
        if (!stack.isEmpty()) {
            top = stack.pop()
        }else{
            return null
        }
        return top
    }
}
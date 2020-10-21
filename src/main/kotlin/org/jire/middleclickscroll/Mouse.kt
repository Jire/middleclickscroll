package org.jire.middleclickscroll

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener

object Mouse : NativeMouseInputListener {

    override fun nativeMouseClicked(e: NativeMouseEvent) {
        if (e.button == NativeMouseEvent.BUTTON2) {
            //println("click ${e.button}")
            synchronized(MiddleClickScroll) {
                MiddleClickScroll.active = !MiddleClickScroll.active
                MiddleClickScroll.clickedX = e.x
                MiddleClickScroll.clickedY = e.y
                //println("active? ${MiddleClickScroll.active}")

                synchronized(TransparentWindow.mutex) {
                    TransparentWindow.drawShape = null
                    TransparentWindow.drawShapeX = if (MiddleClickScroll.active) MiddleClickScroll.clickedX.toFloat() else -1F
                    TransparentWindow.drawShapeY = if (MiddleClickScroll.active) MiddleClickScroll.clickedY.toFloat() else -1F
                    TransparentWindow.isVisible = MiddleClickScroll.active
                }
            }
            //MiddleClickScroll.consume(e)
        } else {
            synchronized(MiddleClickScroll) {
                MiddleClickScroll.active = false
            }
        }
    }

    override fun nativeMouseMoved(e: NativeMouseEvent) {true
        synchronized(MiddleClickScroll) {
            MiddleClickScroll.mouseX = e.x
            MiddleClickScroll.mouseY = e.y
        }
    }

    override fun nativeMouseDragged(e: NativeMouseEvent) {
        //println("drag $e")
    }

}
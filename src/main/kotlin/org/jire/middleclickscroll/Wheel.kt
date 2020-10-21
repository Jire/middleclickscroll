package org.jire.middleclickscroll

import com.github.kwhat.jnativehook.mouse.NativeMouseWheelEvent




import com.github.kwhat.jnativehook.mouse.NativeMouseWheelListener

object Wheel : NativeMouseWheelListener {

    override fun nativeMouseWheelMoved(e: NativeMouseWheelEvent) {
        println("wheel ${e.paramString()}")
    }

}
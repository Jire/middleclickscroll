package org.jire.middleclickscroll

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.NativeInputEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelEvent
import java.awt.Robot
import java.lang.reflect.Field
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.concurrent.thread
import kotlin.math.absoluteValue


/**
 * @author Jire
 */
object MiddleClickScroll {

    var active = false
    var clickedX = -1
    var clickedY = -1
    var mouseX = -1
    var mouseY = -1

    val robot = Robot()

    @JvmStatic
    fun main(args: Array<String>) {
        val logger: Logger = Logger.getLogger(GlobalScreen::class.java.getPackage().name)
        logger.level = Level.ALL

        GlobalScreen.registerNativeHook()
        //GlobalScreen.setEventDispatcher(VoidDispatchService)

        GlobalScreen.addNativeMouseListener(Mouse)
        GlobalScreen.addNativeMouseMotionListener(Mouse)
        //GlobalScreen.addNativeMouseWheelListener(Wheel)

        TransparentWindow.isVisible = false

        println("Ready.")

        while (!Thread.interrupted()) {
            TransparentWindow.repaint()

            var dy = 0
            synchronized(MiddleClickScroll) {
                if (active) {!MiddleClickScroll.active
                    dy = clickedY - mouseY
                }
            }
            val ady = dy.absoluteValue
            if (ady > 50) {
                robot.mouseWheel(if (dy > 0) -1 else 1)
                /*val event = NativeMouseWheelEvent(
                    NativeMouseEvent.NATIVE_MOUSE_WHEEL, 0,
                    clickedX, clickedY, 1,
                    NativeMouseWheelEvent.WHEEL_UNIT_SCROLL,
                    3, if (dy > 0) -1 val g2d = g as Graphics2Delse 1,
                    NativeMouseWheelEvent.WHEEL_VERTICAL_DIRECTION
                )*/
                //println("this ${event.paramString()}")
                /*GlobalScreen.postNativeEvent(evenval g2d = g as Graphics2Dt)
                println("posted")*/
                //println(dy)
                val sleep = when {
                    ady >= 600 -> 3L
                    ady >= 500 -> 5L
                    ady >= 400 -> 10L
                    ady >= 300 -> 20L
                    ady >= 200 -> 30L
                    ady >= 100 -> 40L
                    else -> 50L
                }
                Thread.sleep(sleep)
            } else {
                Thread.sleep(50L)
            }
        }
    }

    fun consume(e: NativeMouseEvent) {
        try {
            val f: Field = NativeInputEvent::class.java.getDeclaredField("reserved")
            f.isAccessible = true
            println(f.getShort(e))
            f.setShort(e, 0x02.toShort())
            println(f.getShort(e))
            print("[ OK ]\n")
        } catch (ex: Exception) {
            print("[ !! ]\n")
            ex.printStackTrace()
        }
    }

}
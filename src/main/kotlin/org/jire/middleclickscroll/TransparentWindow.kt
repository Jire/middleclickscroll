package org.jire.middleclickscroll

import java.awt.*

object TransparentWindow : Window(null) {

    var drawShape: Shape? = null
    var drawShapeX = -1F
    var drawShapeY = -1F

    val mutex = Any()

    override fun paint(g: Graphics) {
        val g2d = g as Graphics2D

        var drawShape: Shape?
        var drawShapeX: Float
        var drawShapeY: Float
        synchronized(mutex) {
            drawShape = this.drawShape
            drawShapeX = this.drawShapeX
            drawShapeY = this.drawShapeY
        }

        if (drawShape == null) {
            val f = font.deriveFont(20f)
            val message = "x"
            drawShape = f.createGlyphVector(g2d.fontRenderContext, message)
                .getOutline(drawShapeX, drawShapeY)
            this.shape = drawShape
            synchronized(mutex) {
                this.drawShape = drawShape
            }
        }

        drawShape ?: return
        g.setColor(Color.RED)
        g2d.fill(drawShape!!.bounds)
    }

    override fun update(g: Graphics) = paint(g)

    init {
        isAlwaysOnTop = false
        bounds = graphicsConfiguration.bounds
        background = Color(0, true)
    }

}
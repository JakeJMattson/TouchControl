package me.jakejmattson.touchcontrol.display

import java.awt.*
import java.awt.image.BufferedImage
import javax.swing.JPanel

/**
 * Panel - holds image to display in GUI.
 *
 * @author JakeJMattson
 */
internal class ImagePanel : JPanel() {
    var image: BufferedImage? = null

    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        image ?: return

        val width = image!!.width
        val height = image!!.height

        g.drawImage(image, 0, 0, width, height, null)
        preferredSize = Dimension(width, height)
    }
}
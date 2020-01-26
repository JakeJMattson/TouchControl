package me.jakejmattson.touchcontrol.display

import java.awt.Point
import java.awt.event.*
import java.awt.image.BufferedImage
import javax.swing.JFrame

/**
 * Frame - GUI container for components (holds ImagePanel).
 *
 * @author JakeJMattson
 */
class ImageFrame(title: String) {
    private val imagePanel = ImagePanel()

    private val frame: JFrame = JFrame(title).apply {
        addWindowListener(createWindowListener())
        add(imagePanel)
        isVisible = true
    }

    var isOpen: Boolean = true
        private set

    private fun createWindowListener(): WindowListener {
        return object : WindowAdapter() {
            override fun windowClosing(windowClosed: WindowEvent?) {
                isOpen = false
            }
        }
    }

    fun showImage(image: BufferedImage) {
        imagePanel.image = image
        frame.repaint()
        frame.pack()
    }

    fun setLocation(location: Point) {
        frame.location = location
    }
}
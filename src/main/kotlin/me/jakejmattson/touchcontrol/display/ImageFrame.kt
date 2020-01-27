package me.jakejmattson.touchcontrol.display

import java.awt.Point
import java.awt.event.*
import java.awt.image.BufferedImage
import javax.swing.*

/**
 * Frame - GUI container for components (holds ImagePanel).
 *
 * @author JakeJMattson
 */
class ImageFrame(title: String) {
    private val leftPanel = ImagePanel()
    private val rightPanel = ImagePanel()

    private val frame: JFrame = JFrame(title).apply {
        addWindowListener(createWindowListener())

        add(JPanel().apply {
            add(leftPanel)
            add(rightPanel)
        })

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

    fun showImages(leftImage: BufferedImage, rightImage: BufferedImage) {
        leftPanel.image = leftImage
        rightPanel.image = rightImage
        frame.repaint()
        frame.pack()
    }

    fun setLocation(location: Point) {
        frame.location = location
    }
}
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
    private val frame: JFrame
    private val imagePanel = ImagePanel()
    var isOpen: Boolean = false
        private set

    init {
        isOpen = true

        frame = JFrame(title)
        frame.addWindowListener(createWindowListener())
        frame.add(imagePanel)
        frame.isVisible = true
    }

    /**
     * Create a listener to monitor the frame closing event.
     *
     * @return WindowListener
     */
    private fun createWindowListener(): WindowListener {
        return object : WindowAdapter() {
            override fun windowClosing(windowClosed: WindowEvent?) {
                isOpen = false
            }
        }
    }

    /**
     * Display an image in the frame.
     *
     * @param image
     * Image to be shown
     */
    fun showImage(image: BufferedImage) {
        imagePanel.setImage(image)
        frame.repaint()
        frame.pack()
    }

    fun setLocation(location: Point) {
        frame.location = location
    }
}
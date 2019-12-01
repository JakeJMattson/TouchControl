/*
 * Project Description:
 * This project is designed to be a touch screen without the screen.
 * Current configuration:
 * Optimal:
 * Object to detect should be lighter than background
 * (darker background is better)
 * Class Description:
 * Demo (main) class to run functions
 */

package io.github.jakejmattson.touchcontrol.demo

import io.github.jakejmattson.touchcontrol.display.ImageFrame
import io.github.jakejmattson.touchcontrol.utils.*
import org.bytedeco.javacpp.*
import org.opencv.core.Scalar
import kotlin.system.exitProcess

private const val DEBUG_MODE = true

fun main() {
    Loader.load(opencv_java::class.java)
    val camera = Camera(-1)

    if (!camera.isOpened)
        return

    val cameraWidth = camera.width
    val cameraHeight = camera.height
    val color = Scalar(0.0, 255.0, 0.0)
    val group = createPianoDemo(cameraWidth, cameraHeight, color)

    if (DEBUG_MODE)
        print(group)

    val rawDisplay = ImageFrame("Touch Control")
    val filteredDisplay = if (DEBUG_MODE) ImageFrame("Debug frame") else null
    placeFrames(rawDisplay, filteredDisplay, cameraWidth.toInt())

    val handler = ImageHandler()

    //Give sample background images to subtractor
    for (i in 0..4)
        handler.trainSubtractor(camera.frame)

    while (rawDisplay.isOpen && camera.isOpened) {
        val rawImage = camera.frame
        val filteredImage = handler.filterImage(rawImage)

        group.updateDetectionPoint(filteredImage)
        group.drawOnto(rawImage)
        group.performAction()

        rawDisplay.showImage(rawImage.toBufferedImage())

        if (DEBUG_MODE)
            filteredDisplay!!.showImage(filteredImage.toBufferedImage())
    }

    camera.release()
    exitProcess(0)
}

private fun placeFrames(frame1: ImageFrame, frame2: ImageFrame?, offset: Int) {
    val location = java.awt.Point(0, 0)
    frame1.setLocation(location)
    location.x = offset

    frame2?.setLocation(location)
}
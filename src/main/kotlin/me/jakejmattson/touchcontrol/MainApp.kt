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

package me.jakejmattson.touchcontrol

import me.jakejmattson.touchcontrol.demo.createPianoDemo
import me.jakejmattson.touchcontrol.display.ImageFrame
import me.jakejmattson.touchcontrol.utils.*
import org.bytedeco.javacpp.*
import org.opencv.core.Scalar
import kotlin.system.exitProcess

private const val DEBUG_MODE = true

fun main() {
    Loader.load(opencv_java::class.java)
    val camera = Camera(-1).takeIf { it.isOpened } ?: return

    val cameraWidth = camera.width
    val color = Scalar(0.0, 255.0, 0.0)
    val group = createPianoDemo(cameraWidth, camera.height, color)

    if (DEBUG_MODE)
        print(group)

    val display = ImageFrame("Touch Control")
    val handler = ImageHandler()

    //Give sample background images to subtractor
    for (i in 0..4)
        handler.trainSubtractor(camera.frame)

    while (display.isOpen && camera.isOpened) {
        val rawImage = camera.frame
        val filteredImage = handler.filterImage(rawImage)

        with(group) {
            updateDetectionPoint(filteredImage)
            drawOnto(rawImage)
            performAction()
        }

        display.showImages(rawImage.toBufferedImage(), filteredImage.toBufferedImage())
    }

    camera.release()
    exitProcess(0)
}
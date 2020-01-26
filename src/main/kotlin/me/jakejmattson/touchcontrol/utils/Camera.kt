package me.jakejmattson.touchcontrol.utils

import org.opencv.core.*
import org.opencv.videoio.*
import javax.swing.JOptionPane

/**
 * Utility class containing functionality related to OpenCV VideoCapture.
 *
 * @author JakeJMattson
 */
class Camera(private val rotation: Int? = null, cameraIndex: Int = 0) {

    /**
     * OpenCV VideoCapture to access physical camera
     */
    private val videoCapture: VideoCapture = VideoCapture(cameraIndex)

    val width: Double
        get() = videoCapture.get(Videoio.CAP_PROP_FRAME_WIDTH)

    val height: Double
        get() = videoCapture.get(Videoio.CAP_PROP_FRAME_HEIGHT)

    val isOpened: Boolean
        get() = videoCapture.isOpened

    /**
     * Retrieve a frame (OpenCV matrix) from the camera.
     *
     * @return Frame
     */
    val frame: Mat
        get() {
            val frame = Mat()
            videoCapture.read(frame)

            if (rotation != null)
                Core.flip(frame, frame, rotation)

            return frame
        }

    init {
        if (!isOpened)
            JOptionPane.showMessageDialog(null, "No camera detected!", "Missing hardware",
                JOptionPane.ERROR_MESSAGE)
    }

    /**
     * Return camera control to the operating system.
     */
    fun release() = videoCapture.release()
}
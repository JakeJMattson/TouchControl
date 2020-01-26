package me.jakejmattson.touchcontrol.utils

import org.opencv.core.*
import org.opencv.video.Video
import kotlin.math.sqrt

/**
 * Utility class containing functionality related to image (matrix) operations.
 *
 * @author JakeJMattson
 */
class ImageHandler {
    /**
     * Background model
     */
    private val subtractor = Video.createBackgroundSubtractorMOG2()

    /**
     * Average samples to create a background model.
     *
     * @param image
     * Sample background
     */
    fun trainSubtractor(image: Mat) {
        subtractor.apply(image, Mat(), 0.2)
    }

    /**
     * Apply all filtering required to process the image.
     *
     * @param image
     * Matrix to be filtered
     *
     * @return Filtered image
     */
    fun filterImage(image: Mat): Mat {
        //Create an empty matrix the same size as the current frame
        val filteredImage = Mat(image.rows(), image.cols(), CvType.CV_8UC1, Scalar.all(0.0))

        //Get background model
        val background = Mat()
        subtractor.getBackgroundImage(background)

        //Calculate absolute difference between background model and current frame
        val diffImage = Mat()
        Core.absdiff(background, image, diffImage)

        //Extract foreground
        val threshold = 128.0f
        for (j in 0 until diffImage.rows())
            for (i in 0 until diffImage.cols()) {
                val pixel = diffImage.get(j, i)
                val dist = sqrt(pixel[0] * pixel[0] + pixel[1] * pixel[1] + pixel[2] * pixel[2]).toFloat()

                if (dist > threshold)
                    filteredImage.put(j, i, 255.0)
            }

        return filteredImage
    }
}
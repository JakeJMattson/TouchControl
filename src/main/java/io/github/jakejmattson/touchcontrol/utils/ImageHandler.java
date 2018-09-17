package io.github.jakejmattson.touchcontrol.utils;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.*;

import java.awt.image.BufferedImage;

/**
 * Utility class containing functionality related to image (matrix) operations.
 *
 * @author JakeJMattson
 */
public class ImageHandler
{
	/**
	 * Background model
	 */
	private final BackgroundSubtractorMOG2 subtractor;

	public ImageHandler()
	{
		subtractor = Video.createBackgroundSubtractorMOG2();
	}

	/**
	 * Average samples to create a background model.
	 *
	 * @param image
	 * 		Sample background
	 */
	public void trainSubtractor(Mat image)
	{
		subtractor.apply(image, new Mat(), 0.2);
	}

	/**
	 * Apply all filtering required to process the image.
	 *
	 * @param image
	 * 		Matrix to be filtered
	 *
	 * @return Filtered image
	 */
	public Mat filterImage(Mat image)
	{
		//Create an empty matrix the same size as the current frame
		Mat filteredImage = new Mat(image.rows(), image.cols(), CvType.CV_8UC1, Scalar.all(0));

		//Get background model
		Mat background = new Mat();
		subtractor.getBackgroundImage(background);

		//Calculate absolute difference between background model and current frame
		Mat diffImage = new Mat();
		Core.absdiff(background, image, diffImage);

		//Extract foreground
		float threshold = 128.0F;
		for (int j = 0; j < diffImage.rows(); ++j)
			for (int i = 0; i < diffImage.cols(); ++i)
			{
				double[] pixel = diffImage.get(j, i);

				float dist = (float) (pixel[0] * pixel[0] + pixel[1] * pixel[1] + pixel[2] * pixel[2]);
				dist = (float) Math.sqrt(dist);

				if (dist > threshold)
					filteredImage.put(j, i, 255);
			}

		return filteredImage;
	}

	/**
	 * Convert an OpenCV Mat to a Java BufferedImage.
	 *
	 * @param matrix
	 * 		OpenCV Mat
	 *
	 * @return BufferedImage
	 */
	public BufferedImage convertMatToImage(Mat matrix)
	{
		//Get image dimensions
		int width = matrix.width();
		int height = matrix.height();

		int type = matrix.channels() != 1 ? BufferedImage.TYPE_3BYTE_BGR : BufferedImage.TYPE_BYTE_GRAY;

		if (type == BufferedImage.TYPE_3BYTE_BGR)
			Imgproc.cvtColor(matrix, matrix, Imgproc.COLOR_BGR2RGB);

		//Get matrix data
		byte[] data = new byte[width * height * (int) matrix.elemSize()];
		matrix.get(0, 0, data);

		//Create image with matrix data
		BufferedImage out = new BufferedImage(width, height, type);
		out.getRaster().setDataElements(0, 0, width, height, data);

		return out;
	}
}
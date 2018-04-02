/**
 * Class Description:
 * Handler - performs image (matrix) operations such as filtering and converting
 */

package io.github.mattson543.touchcontrol.utils;

import java.awt.image.BufferedImage;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.*;

public class ImageHandler
{
	private final BackgroundSubtractorMOG2 subtractor;

	//Constructors
	public ImageHandler()
	{
		//Create subtractor
		subtractor = Video.createBackgroundSubtractorMOG2();
	}

	//Setters

	//Getters

	//Class methods
	public void trainSubtractor(Mat image)
	{
		//Average samples to create background model
		subtractor.apply(image, new Mat(), 0.2);
	}

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
		float threshold = 128.0f;
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

	public BufferedImage convertMatToImage(Mat matrix)
	{
		//Get image dimensions
		int width = matrix.width();
		int height = matrix.height();

		//Determine image type
		int type;

		if (matrix.channels() != 1)
		{
			type = BufferedImage.TYPE_3BYTE_BGR;
			Imgproc.cvtColor(matrix, matrix, Imgproc.COLOR_BGR2RGB);
		}
		else
			type = BufferedImage.TYPE_BYTE_GRAY;

		//Get matrix data
		byte[] data = new byte[width * height * (int) matrix.elemSize()];
		matrix.get(0, 0, data);

		//Create image with matrix data
		BufferedImage out = new BufferedImage(width, height, type);
		out.getRaster().setDataElements(0, 0, width, height, data);

		return out;
	}
}
package touchcontrol.filter;

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
		subtractor.apply(image, new Mat(), 0.5);
	}

	public Mat filterImage(Mat image)
	{
		//Filters
		Scalar backgroundColor = new Scalar(0, 0, 0);
		Scalar rangeColor = new Scalar(128, 128, 128);

		//Create matricies
		Mat filteredImage = new Mat();
		Mat background = new Mat();

		//Get background model
		subtractor.getBackgroundImage(background);

		//Remove background
		Core.subtract(image, background, filteredImage);

		//Apply filters
		Core.inRange(filteredImage, backgroundColor, rangeColor, filteredImage);

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
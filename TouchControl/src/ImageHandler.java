import java.awt.image.BufferedImage;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

public class ImageHandler
{
	private Scalar backgroundColor;
	private Scalar rangeColor;

	private final static Scalar DEFAULT_BACKGROUND = new Scalar(0, 0, 0);
	private final static Scalar DEFAULT_RANGE = new Scalar(175, 175, 175);

	//Constructors
	public ImageHandler()
	{
		this(DEFAULT_BACKGROUND, DEFAULT_RANGE);
	}

	public ImageHandler(Scalar backgroundColor, Scalar rangeColor)
	{
		this.backgroundColor = backgroundColor;
		this.rangeColor = rangeColor;
	}

	//Setters
	public void setBackgroundColor(Scalar backgroundColor)
	{
		this.backgroundColor = backgroundColor;
	}

	public void setRangeColor(Scalar rangeColor)
	{
		this.rangeColor = rangeColor;
	}

	public void setColors(Scalar backgroundColor, Scalar rangeColor)
	{
		this.backgroundColor = backgroundColor;
		this.rangeColor = rangeColor;
	}

	//Getters

	//Class methods
	public Mat filterImage(Mat image)
	{
		//Create matrices
		Mat hsvImage = new Mat();
		Mat grayImage = new Mat();
		Mat filteredImage = new Mat();

		//Filter image
		Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
		Core.inRange(image, backgroundColor, rangeColor, hsvImage);
		filteredImage = filterNoise(hsvImage);

		return filteredImage;
	}

	private Mat filterNoise(Mat image)
	{
		//Create matrix
		Mat modifiedImage = new Mat();

		//Remove noise from image
		Imgproc.erode(image, modifiedImage, createStructuringElement(new Size(7, 7)));
		Imgproc.dilate(modifiedImage, modifiedImage, createStructuringElement(new Size(2, 2)));

		return modifiedImage;
	}

	private Mat createStructuringElement(Size size)
	{
		//Create matrix
		return Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, size);
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
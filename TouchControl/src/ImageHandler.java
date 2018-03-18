import java.awt.image.BufferedImage;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

public class ImageHandler
{
	private Scalar backgroundColor;
	private Scalar rangeColor;

	private final Scalar DEFAULT_BACKGROUND = new Scalar(0, 0, 0);
	private final Scalar DEFAULT_RANGE = new Scalar(175, 175, 175);

	//Constructors
	public ImageHandler()
	{
		this.backgroundColor = DEFAULT_BACKGROUND;
		this.rangeColor = DEFAULT_RANGE;
	}

	public ImageHandler(Scalar backgroundColor, Scalar rangeColor)
	{
		this.backgroundColor = backgroundColor;
		this.rangeColor = rangeColor;
	}

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

	//Class methods
	public Mat filterImage(Mat image)
	{
		//Local variables
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
		//Local Variables
		Mat modifiedImage = new Mat();
		Size erodeSize = new Size(7, 7);
		Size dilateSize = new Size(2, 2);

		//Remove noise from image
		Imgproc.erode(image, modifiedImage, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, erodeSize));
		Imgproc.dilate(modifiedImage, modifiedImage, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, dilateSize));

		return modifiedImage;
	}

	public BufferedImage convertMatToImage(Mat matrix)
	{
		//Local variables
		int width = matrix.width();
		int height = matrix.height();
		int type = BufferedImage.TYPE_BYTE_GRAY;
		byte[] data = new byte[width * height * (int) matrix.elemSize()];
		BufferedImage out;

		//Determine type
		if (matrix.channels() != 1)
		{
			type = BufferedImage.TYPE_3BYTE_BGR;
			Imgproc.cvtColor(matrix, matrix, Imgproc.COLOR_BGR2RGB);
		}

		//Get matrix data
		matrix.get(0, 0, data);

		//Create image and pass matrix data
		out = new BufferedImage(width, height, type);
		out.getRaster().setDataElements(0, 0, width, height, data);

		return out;
	}
}
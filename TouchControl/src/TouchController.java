/*Notes and ideas:
 *This project is designed to be a touch screen without the screen.
 *
 *Current configuration:
 *Background must be black
 *Camera should be rotated 180 degrees
 */

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class TouchController
{
	public static void main(String[] args)
	{
		//Create instance of class
		TouchController driver = new TouchController();
		driver.start();
	}

	private void start()
	{
		//Load OpenCV
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		//Begin Capture
		try
		{
			capture();
		}
		catch(Exception e)
		{
			System.out.println("Exception: " + e.getClass().getSimpleName());
		}

		//Exit
		System.out.println("Program terminated.");
		System.exit(0);
	}

	private void capture()
	{
		//Local variables
	    Mat rawImage = new Mat();
	    Mat filteredImage = new Mat();
	    Point mousePoint;
	    Point adjustedMousePoint;
	    Point previousMousePoint = new Point(0,0);
	    Point volumePoint;
	    int previousVolume = 100;
	    int volume;
	    int shiftFromEdges = 10;
	    int numOfDivisions = 100;
	    double divisionSize;
	    Rect mousePad;
	    Rect volumeSlider;

	    //Start camera
	    VideoCapture camera = new VideoCapture(0);

	    //Get camera properties
	    double cameraWidth = camera.get(Videoio.CAP_PROP_FRAME_WIDTH);
	    double cameraHeight = camera.get(Videoio.CAP_PROP_FRAME_HEIGHT);

	    //Create display
	    ImageDisplay display = new ImageDisplay((int)cameraWidth, (int)cameraHeight);
	    display.buildGUI();
	    
	    //Create components
	    mousePad = new Rect(new Point(shiftFromEdges, shiftFromEdges), 
	    					new Point(0.75 * cameraWidth - shiftFromEdges, cameraHeight - shiftFromEdges));
	    volumeSlider = new Rect(new Point(0.75 * cameraWidth + shiftFromEdges, shiftFromEdges),
	    						new Point(cameraWidth - shiftFromEdges, cameraHeight - shiftFromEdges));
	    
	    //Create sectors, each representing a slider setting
	    divisionSize = calculateDivisionSize(volumeSlider.height, numOfDivisions);
	    
	    //Wait for camera to get images before proceeding
	    while (rawImage.empty())
	    {
			camera.read(rawImage);
	    }

	    //While frame is not closed
    	while (display.getStatus())
    	{
    		//Read image from camera
    		camera.read(rawImage);
    		
    		//Flip image 180 degrees for screen viewing
    		Core.flip(rawImage, rawImage , -1);
    		
    		//Filter for processing
    		filteredImage = filterImage(rawImage);

    		//Draw components onto image
    		drawRect(rawImage, mousePad);
    		drawRect(rawImage, volumeSlider);
    		
    		//Find the farthest point that is not background
			mousePoint = findFarthestPoint(filteredImage, mousePad);
			volumePoint = findFarthestPoint(filteredImage, volumeSlider);
			
			//If a non-background point is found in range
			if (mousePoint != null)
			{
				//Mark detection
				Imgproc.circle(rawImage, mousePoint, 10, new Scalar(0, 255, 0));
				
				//Check proximity - limit jumpiness of mouse; decreases fluidity
				if (Math.abs(mousePoint.x - previousMousePoint.x) > 4 &&
					Math.abs(mousePoint.y - previousMousePoint.y) > 2)
				{
					//Convert point in range to point on screen
					adjustedMousePoint = adjustPoint(mousePoint, mousePad);
					
					moveMouse(adjustedMousePoint);
					previousMousePoint = mousePoint;
				}
			}
			
			//If a non-background point is found in range
			if (volumePoint != null)
			{
				//Mark detection
				Imgproc.circle(rawImage, volumePoint, 10, new Scalar(0, 255, 0));

				//Determine the volume sector in which the point is located
				volume = determineDivision(volumePoint, shiftFromEdges, divisionSize);

				//
				if (volume != previousVolume)
				{
				    if (volume >= 0 && volume <= numOfDivisions)
				    {
				    	previousVolume = volume;
						volume = numOfDivisions - volume;
						setVolume(volume);
				    }
				}
			}
			
			setSliderStatus(rawImage, volumeSlider, previousVolume, divisionSize);

			showImage(display, rawImage);

			//Cleanup to avoid memory exception
			rawImage.release();
		    filteredImage.release();
    	}

    	//Return camera control to OS
    	camera.release();
	}

	private void setSliderStatus(Mat rawImage, Rect slider, int division, double divisionSize)
	{
		//Local variables
		double yPosition;
		Scalar infoColor = new Scalar(0, 255, 0); //Scalar is BGR
		int shift;
		
		//Determine status
		if (100 - division >= 50)
			shift = 18;
		else
			shift = -8;
		
		yPosition = divisionSize * division + slider.y;
		
		//Draw status
		Imgproc.line(rawImage, new Point(slider.x, yPosition),
				 new Point(slider.x + slider.width, yPosition), infoColor, 3);
		Imgproc.putText(rawImage, 100 - division + "%", new Point(slider.x + 5, yPosition + shift),
					Core.FONT_HERSHEY_COMPLEX, 0.5, infoColor);
	}

	private double calculateDivisionSize(double sliderWidth, int numOfDivisions)
	{
		//Local variables
		double divisionSize;

		//Calculations
		divisionSize = sliderWidth / numOfDivisions;

		return divisionSize;
	}
	
	private int determineDivision(Point farthestPoint, int shift, double divisionSize)
	{
		//Local variables
		int division;

		//Calculations
		division = (int) Math.ceil(((farthestPoint.y - shift) / divisionSize));

		return division;
	}
	
	private void drawRect(Mat rawImage, Rect slider)
	{
		Point upperLeft;
		Point lowerRight;
		Scalar sliderColor = new Scalar(255, 0, 0); //Scalar is BGR
		new Scalar(0, 255, 0);

		//Determine slider state
		upperLeft = new Point(slider.x, slider.y);
		lowerRight = new Point(slider.x + slider.width, slider.y + slider.height);

		//Draw components
		Imgproc.rectangle(rawImage, upperLeft, lowerRight, sliderColor, 3);
	}
	
	private Mat filterImage(Mat image)
	{
		//Local variables
		int scalar = 175;
		Mat hsvImage = new Mat();
		Mat grayImage = new Mat();
		Mat filteredImage = new Mat();
		Scalar backgroundColor = new Scalar(0, 0, 0);
		Scalar rangeColor = new Scalar(scalar, scalar, scalar);

		//Filter image
		Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
		hsvImage = colorToHSV(grayImage, backgroundColor, rangeColor);
	    filteredImage = filterNoise(hsvImage);

		return filteredImage;
	}

	private Mat colorToHSV(Mat image, Scalar lowerBound, Scalar upperBound)
	{
		//Local variables
		Mat hsvImage = new Mat();

		//Convert image
		Core.inRange(image, lowerBound, upperBound, hsvImage);

		return hsvImage;
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

	private Point findFarthestPoint(Mat filteredImage, Rect range)
	{
		//Local variables
		Point farthestPoint = new Point();
		boolean doneSearching = false;
		double[] pixelColor;

		//Starting in the lower right corner, search for non-background pixel
		outerLoop:
		for (int y = range.y; y < range.y + range.height; y++)
		{
			innerLoop:
			for (int x = range.x + range.width; x >= range.x; x--)
			{
				//Get pixel at coordinate
				pixelColor = filteredImage.get(y, x);

				//Check pixel for non-background coloring
				if (pixelColor[0] != 255.0)
				{
					farthestPoint.x = x;
					farthestPoint.y = y;
					doneSearching = true;
				}
					
				if (doneSearching)
					break innerLoop;
			}
			if (doneSearching)
				break outerLoop;
		}

		//No non-background pixel found
		if (farthestPoint.x == range.x || farthestPoint.y == range.y ||
			farthestPoint.x == 0	   || farthestPoint.y == 0)
		{
			farthestPoint = null;
		}

		return farthestPoint;
	}
	
	private Point adjustPoint(Point imagePoint, Rect component)
	{
		//Local variables
		Point adjustedPoint = new Point();
		double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		double widthAdjustment = screenWidth / component.width;
		double heightAdjustment = screenHeight / component.height;
		
		//Adjust point
		adjustedPoint.x = imagePoint.x * widthAdjustment;
		adjustedPoint.y = imagePoint.y * heightAdjustment;
		
		return adjustedPoint;
	}

	private void moveMouse(Point adjustedPoint)
	{
		//Local variables
		Robot mouseMover = createRobot();
		
		//Move mouse to adjusted point
		mouseMover.mouseMove((int) adjustedPoint.x, (int) adjustedPoint.y);
	}
	
	private Robot createRobot()
	{
		//Local variable
		Robot robot = null;
		
		//Create robot
		try
		{
			robot = new Robot();
		}
		catch (AWTException e)
		{
			
		}
		
		return robot;
	}

	private void setVolume(int volume)
	{
		//Nircmd allows volume changing
		File nircmdPath = new File("./nircmd.exe");

    	System.out.println("Volume set to: " + volume);
    	
    	//Calculate volume in nircmd terms
        double endVolume = 655.35 * volume;

        try
        {
        	//Set volume
        	Runtime.getRuntime().exec(nircmdPath.getCanonicalPath() + " setsysvolume " + endVolume);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
	}
	
	private void showImage(ImageDisplay display, Mat rawImage)
	{
		//Local variables
		MatToImage converter = new MatToImage();
	    BufferedImage convertedImage;

	    //Show image
		converter.setMatrix(rawImage, ".jpg");
		convertedImage = converter.getBufferedImage();
		display.setImage(convertedImage);
		display.repaint();
	}
}
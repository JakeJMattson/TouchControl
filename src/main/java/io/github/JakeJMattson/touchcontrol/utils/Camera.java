package io.github.JakeJMattson.touchcontrol.utils;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

/**
 * Utility class containing functionality related to OpenCV VideoCapture.
 *
 * @author JakeJMattson
 */
public class Camera
{
	/**
	 * OpenCV VideoCapture to access physical camera
	 */
	private final VideoCapture videoCapture;
	/**
	 * How each frame will be flipped upon reading
	 */
	private final Integer rotation;

	/**
	 * When no index is specified, '0' can be used to request
	 * the default camera available to the operating system
	 */
	private static final int DEFAULT_CAMERA = 0;

	public Camera()
	{
		this(null, DEFAULT_CAMERA);
	}

	public Camera(Integer rotation)
	{
		this(rotation, DEFAULT_CAMERA);
	}

	public Camera(Integer rotation, int cameraIndex)
	{
		this.rotation = rotation;
		videoCapture = new VideoCapture(cameraIndex);
	}

	/**
	 * Get the width dimension of the camera resolution.
	 *
	 * @return Width
	 */
	public double getWidth()
	{
		return videoCapture.get(Videoio.CAP_PROP_FRAME_WIDTH);
	}

	/**
	 * Get the height dimension of the camera resolution.
	 *
	 * @return Height
	 */
	public double getHeight()
	{
		return videoCapture.get(Videoio.CAP_PROP_FRAME_HEIGHT);
	}

	/**
	 * Retrieve a frame (OpenCV matrix) from the camera.
	 * Flip the frame if requested.
	 *
	 * @return Frame
	 */
	public Mat getFrame()
	{
		Mat frame = new Mat();

		videoCapture.read(frame);

		if (rotation != null)
			Core.flip(frame, frame, rotation);

		return frame;
	}

	/**
	 * Whether or not the camera was successfully initialized.
	 *
	 * @return Open status
	 */
	public boolean isOpened()
	{
		return videoCapture.isOpened();
	}

	/**
	 * Return camera control to the operating system.
	 */
	public void release()
	{
		videoCapture.release();
	}
}
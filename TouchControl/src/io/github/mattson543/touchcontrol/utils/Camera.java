package io.github.mattson543.touchcontrol.utils;

import org.opencv.core.*;
import org.opencv.videoio.*;

/**
 * Utility class containing functionality related to OpenCV VideoCapture.
 *
 * @author mattson543
 */
public class Camera
{
	/**
	 * OpenCV VideoCapture to access physical camera.
	 */
	private final VideoCapture camera;
	/**
	 * How each frame will be flipped upon reading.
	 */
	private final Integer rotation;

	/**
	 * When no index is specified, '0' can be used to request
	 * the default camera available to the operating system.
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
		camera = new VideoCapture(cameraIndex);
	}

	/**
	 * Get the width dimension of the camera resolution.
	 *
	 * @return Width
	 */
	public double getWidth()
	{
		return camera.get(Videoio.CAP_PROP_FRAME_WIDTH);
	}

	/**
	 * Get the height dimension of the camera resolution.
	 *
	 * @return Height
	 */
	public double getHeight()
	{
		return camera.get(Videoio.CAP_PROP_FRAME_HEIGHT);
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

		camera.read(frame);

		if (rotation != null)
			Core.flip(frame, frame, rotation);

		return frame;
	}

	/**
	 * Whether or not the camera was successfully initialized.
	 * Returns false if no camera is plugged in.
	 */
	public boolean isOpened()
	{
		return camera.isOpened();
	}

	/**
	 * Return camera control to the operating system.
	 */
	public void release()
	{
		camera.release();
	}
}
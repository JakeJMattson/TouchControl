package io.github.mattson543.touchcontrol.utils;

import org.opencv.core.*;
import org.opencv.videoio.*;

public class Camera
{
	private VideoCapture camera;
	private Integer rotation;

	private static final int DEFAULT_CAMERA = 0;

	public Camera()
	{
		this(DEFAULT_CAMERA);
	}

	public Camera(int cameraIndex)
	{
		this(cameraIndex, null);
	}

	public Camera(int cameraIndex, Integer rotation)
	{
		this.camera = new VideoCapture(cameraIndex);
		this.rotation = rotation;
	}

	public double getWidth()
	{
		return camera.get(Videoio.CAP_PROP_FRAME_WIDTH);
	}

	public double getHeight()
	{
		return camera.get(Videoio.CAP_PROP_FRAME_HEIGHT);
	}

	public Mat getFrame()
	{
		Mat frame = new Mat();

		camera.read(frame);

		if (rotation != null)
			Core.flip(frame, frame, rotation);

		return frame;
	}

	public boolean isOpened()
	{
		return camera.isOpened();
	}

	public void release()
	{
		camera.release();
	}
}
package io.github.jakejmattson.touchcontrol.display;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Frame - GUI container for components (holds ImagePanel).
 *
 * @author JakeJMattson
 */
public class ImageFrame
{
	private final JFrame frame;
	private final ImagePanel imagePanel;
	private boolean isOpen;

	public ImageFrame(String title)
	{
		imagePanel = new ImagePanel();
		isOpen = true;

		frame = new JFrame(title);
		frame.addWindowListener(createWindowListener());
		frame.add(imagePanel);
		frame.setVisible(true);
	}

	public boolean isOpen()
	{
		return isOpen;
	}

	/**
	 * Create a listener to monitor the frame closing event.
	 *
	 * @return WindowListener
	 */
	private WindowListener createWindowListener()
	{
		return new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent windowClosed)
			{
				isOpen = false;
			}
		};
	}

	/**
	 * Display an image in the frame.
	 *
	 * @param image
	 * 		Image to be shown
	 */
	public void showImage(BufferedImage image)
	{
		imagePanel.setImage(image);
		frame.repaint();
		frame.pack();
	}

	public void setLocation(Point location)
	{
		frame.setLocation(location);
	}
}
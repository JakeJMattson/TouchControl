package io.github.jakejmattson.touchcontrol.display;

import javax.swing.JFrame;
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
	/**
	 * Panel to hold/display a BufferedImage
	 */
	private final ImagePanel imagePanel;
	/**
	 * Whether or not the frame is currently open
	 */
	private boolean isOpen;

	public ImageFrame(String title)
	{
		frame = new JFrame(title);
		imagePanel = new ImagePanel();
		isOpen = true;

		frame.addWindowListener(createWindowListener());
		frame.add(imagePanel);
		frame.setVisible(true);
	}

	/**
	 * Externally called to see if display frame is still open.
	 *
	 * @return Open status
	 */
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
				//Set window closing events
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
		//Send image to panel
		imagePanel.setImage(image);

		//Redraw frame
		frame.repaint();

		//Resize frame to fit image
		frame.pack();
	}
}
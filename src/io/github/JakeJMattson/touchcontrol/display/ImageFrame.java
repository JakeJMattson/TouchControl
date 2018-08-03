package io.github.JakeJMattson.touchcontrol.display;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

/**
 * Frame - GUI container for components (holds ImagePanel).
 *
 * @author JakeJMattson
 */
@SuppressWarnings("serial")
public class ImageFrame extends JFrame
{
	/**
	 * Whether or not the frame is currently open
	 */
	private boolean isOpen;
	/**
	 * Panel to hold/display a BufferedImage
	 */
	private ImagePanel imagePanel;

	public ImageFrame(String title)
	{
		super();
		buildGUI(title);
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
	 * Construct the display and its children.
	 *
	 * @param title Title of the frame
	 */
	private void buildGUI(String title)
	{
		//Create frame
		setTitle(title);
		addWindowListener(createWindowListener());
		imagePanel = new ImagePanel();
		add(imagePanel);
		setVisible(true);
		isOpen = true;
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
	 * @param image Image to be shown
	 */
	public void showImage(BufferedImage image)
	{
		//Send image to panel
		imagePanel.setImage(image);

		//Redraw frame
		this.repaint();

		//Resize frame to fit image
		pack();
	}
}
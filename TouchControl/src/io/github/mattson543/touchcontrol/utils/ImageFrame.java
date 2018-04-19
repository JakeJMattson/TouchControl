/**
 * Class Description:
 * Frame - GUI container for components (holds ImagePanel)
 */

package io.github.mattson543.touchcontrol.utils;

import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ImageFrame extends JFrame
{
	private boolean isOpen;
	private ImagePanel imagePanel;

	//Constructors
	public ImageFrame()
	{
		super();
		buildGUI();
	}

	//Setters

	//Getters
	public boolean isOpen()
	{
		return isOpen;
	}

	//Class methods
	private void buildGUI()
	{
		//Create frame
		setTitle("Touch Control");
		addWindowListener(createWindowListener());
		imagePanel = new ImagePanel();
		add(imagePanel);
		setVisible(true);
		isOpen = true;
	}

	private WindowListener createWindowListener()
	{
		WindowListener listener = new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent windowClosed)
			{
				//Set window closing events
				isOpen = false;
			}
		};

		return listener;
	}

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
/**
 * Class Description:
 * Panel - holds image to display in GUI
 */

package io.github.mattson543.touchcontrol.display;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel
{
	private BufferedImage image;

	//Constructors
	public ImagePanel()
	{
		super();
	}

	//Setters

	//Getters

	//Class methods
	public void setImage(BufferedImage image)
	{
		this.image = image;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		if (image != null)
		{
			//Get image dimensions
			int width = image.getWidth();
			int height = image.getHeight();

			//Draw image onto panel
			g.drawImage(image, 0, 0, width, height, null);

			//Set panel size
			setPreferredSize(new Dimension(width, height));
		}
	}
}
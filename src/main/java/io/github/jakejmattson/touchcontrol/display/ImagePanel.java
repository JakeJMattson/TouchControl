package io.github.jakejmattson.touchcontrol.display;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Panel - holds image to display in GUI.
 *
 * @author JakeJMattson
 */
class ImagePanel extends JPanel
{
	private BufferedImage image;

	void setImage(BufferedImage image)
	{
		this.image = image;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		if (image != null)
		{
			int width = image.getWidth();
			int height = image.getHeight();

			g.drawImage(image, 0, 0, width, height, null);
			setPreferredSize(new Dimension(width, height));
		}
	}
}
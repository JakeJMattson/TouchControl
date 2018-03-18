import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

@SuppressWarnings("serial")
public class ImageDisplay extends JPanel
{
	JFrame frame;
	private BufferedImage image;
	private boolean openStatus;

	public ImageDisplay()
	{
		super(); //JPanel
		buildGUI();
	}

	public void showImage(BufferedImage image)
	{
		this.image = image;
		this.repaint();
		frame.pack();
	}

	//Add content to frame
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (image != null)
		{
			int width = image.getWidth();
			int height = image.getHeight();

			//Add image
			g.drawImage(image, 0, 0, width, height, null);
			this.setPreferredSize(new Dimension(width, height));
		}
	}

	public void buildGUI()
	{
		frame = new JFrame();
		frame = setPreferences(frame);
		frame.add(this);
		frame.setVisible(true);
		openStatus = true;
	}

	private JFrame setPreferences(JFrame frame)
	{
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.setTitle("Touch Control");
		frame.setSize(this.getWidth() + 16, this.getHeight() + 39);
		frame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent windowClosed)
			{
				openStatus = false;
				frame.dispose();
			}
		});

		return frame;
	}

	public boolean getStatus()
	{
		return this.openStatus;
	}
}
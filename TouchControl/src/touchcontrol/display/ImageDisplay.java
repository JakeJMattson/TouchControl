package touchcontrol.display;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

@SuppressWarnings("serial")
public class ImageDisplay extends JPanel
{
	private JFrame frame;
	private BufferedImage image;
	private boolean isOpen;

	//Constructors
	public ImageDisplay()
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
		//Close preferences
		int closeOperation = WindowConstants.DO_NOTHING_ON_CLOSE;
		WindowListener listener = createWindowListener();

		//Create frame
		frame = new JFrame();
		frame.setTitle("Touch Control");
		frame.setDefaultCloseOperation(closeOperation);
		frame.addWindowListener(listener);
		frame.add(this);
		frame.setVisible(true);
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
				frame.dispose();
			}
		};

		return listener;
	}

	public void showImage(BufferedImage image)
	{
		//Store image
		this.image = image;

		//Draw new image
		this.repaint();

		//Resize frame to fit image
		frame.pack();
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

			//Add image to panel
			g.drawImage(image, 0, 0, width, height, null);

			//Set panel size
			setPreferredSize(new Dimension(width, height));
		}
	}
}
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImageDisplay extends JPanel
{
	private BufferedImage image;
	private boolean openStatus;
	
	public ImageDisplay(int width, int height)
	{
		super(); //JPanel
		this.setSize(width, height);
	}
	
	public void setImage(BufferedImage image)
	{
		this.image = image;
	}

	//Add content to frame
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (image == null)
		{
			return;
		}
		
		//Add image
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
	}

	public void buildGUI()
	{
		JFrame frame = new JFrame();
		frame = setPreferences(frame);
		frame.add(this);
		frame.setVisible(true);
	    openStatus = true;
	}
	
	private JFrame setPreferences(JFrame frame)
	{
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    frame.setTitle("Slider");
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
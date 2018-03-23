/**
 * Class Description:
 * Apply the same function over many Touchable objects
 */

package touchcontrol.touchables;

import java.util.ArrayList;

import org.opencv.core.*;

public class TouchableGroup
{
	ArrayList<Touchable> components;

	//Constructors
	public TouchableGroup(Touchable...components)
	{
		this.components = new ArrayList<>();

		for (Touchable component : components)
			this.components.add(component);

		checkCollision();
	}

	//Setters
	public void setColor(Scalar color)
	{
		for (Touchable component : components)
			component.setColor(color);
	}

	public void addComponent(Touchable component)
	{
		components.add(component);

		checkCollision();
	}

	public void removeComponent(Touchable component)
	{
		components.remove(component);
	}

	//Getters
	public ArrayList<Touchable> getComponents()
	{
		return components;
	}

	//Class actions
	public void updateDetectionPoint(Mat filteredImage)
	{
		for (Touchable component : components)
			component.updateDetectionPoint(filteredImage);
	}

	public void drawOnto(Mat rawImage)
	{
		for (Touchable component : components)
			component.drawOnto(rawImage);
	}

	public void performAction()
	{
		for (Touchable component : components)
			component.performAction();
	}

	@Override
	public String toString()
	{
		String groupData = "Touchable objects in group (" + super.toString() + "): " + components.size() + " \n\n";

		for (int i = 0; i < components.size(); i++)
			groupData += "(" + i + ")" + components.get(i).toString() + "\n";

		return groupData;
	}

	private void checkCollision()
	{
		for (int i = 0; i < components.size(); i++)
			for (int j = i + 1; j < components.size(); j++)
			{
				//Get components dimensions
				Rect dim1 = components.get(i).getDimensions();
				Rect dim2 = components.get(j).getDimensions();

				//Check if two components overlap
				boolean overlaps = !(dim1.y + dim1.height <= dim2.y
						|| dim1.y >= dim2.y + dim2.height
						|| dim1.x + dim1.width <= dim2.x
						|| dim1.x >= dim2.x + dim2.width);

				//Print warning
				if (overlaps)
					System.out.println("Touchable colision warning in " + super.toString() +
							" between component " + i + " and component " + j);
			}
	}
}
package io.github.JakeJMattson.touchcontrol.utils;

import java.util.*;

import org.opencv.core.*;

import io.github.JakeJMattson.touchcontrol.touchables.Touchable;

/**
 * Apply the same function over many Touchable objects.
 *
 * @author JakeJMattson
 */
public class TouchableGroup
{
	/**
	 * A List of all components in the group
	 */
	private final ArrayList<Touchable> components;

	public TouchableGroup(Touchable...components)
	{
		this.components = new ArrayList<>();

		for (Touchable component : components)
			this.components.add(component);

		checkCollision();
	}

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

	public List<Touchable> getComponents()
	{
		return components;
	}

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

	/**
	 * Check whether or not any of the components in
	 * the group overlaps (collides) with another.
	 * Note: This has no return. It is only to print warnings.
	 */
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

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final String NEWLINE = System.lineSeparator();

		StringBuilder groupData = new StringBuilder();

		groupData.append("Touchable objects in group (" + super.toString() + "): "
				+ components.size() + NEWLINE + NEWLINE);

		for (int i = 0; i < components.size(); i++)
			groupData.append("(" + i + ")" + components.get(i).toString() + NEWLINE);

		return groupData.toString();
	}
}
package io.github.jakejmattson.touchcontrol.utils;

import io.github.jakejmattson.touchcontrol.touchables.Touchable;
import org.opencv.core.*;

public class Grid
{
	private final int rowCount, colCount;
	private final double cellWidth, cellHeight;
	private final int padding;

	public Grid(int rowCount, int colCount, double totalWidth, double totalHeight, int padding)
	{
		this.rowCount = rowCount;
		this.colCount = colCount;
		cellWidth = totalWidth / rowCount;
		cellHeight = totalHeight / colCount;
		this.padding = padding;
	}

	public boolean align(Touchable component, Point topLeftCell, Point bottomRightCell)
	{
		if (component == null)
			return false;

		if (!validatePoint(topLeftCell))
			return false;

		if (!validatePoint(bottomRightCell))
			return false;

		Point newTopLeft = new Point();
		newTopLeft.x = topLeftCell.x * cellWidth + padding;
		newTopLeft.y = topLeftCell.y * cellHeight + padding;

		Point newBottomRight = new Point();
		newBottomRight.x = (bottomRightCell.x + 1) * cellWidth - padding;
		newBottomRight.y = (bottomRightCell.y + 1) * cellHeight - padding;

		Rect newDim = new Rect(newTopLeft, newBottomRight);
		component.setDimensions(newDim);

		return true;
	}

	private boolean validatePoint(Point point)
	{
		if (point.x < 0 || point.x > rowCount)
			return false;

		if (point.y < 0 || point.y > colCount)
			return false;

		return true;
	}
}

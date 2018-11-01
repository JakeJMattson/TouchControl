package io.github.jakejmattson.touchcontrol.utils;

import org.opencv.core.*;

public class Grid
{
	private final double cellWidth;
	private final double cellHeight;
	private final int padding;

	public Grid(int rowCount, int colCount, double totalWidth, double totalHeight, int padding)
	{
		cellWidth = totalWidth / rowCount;
		cellHeight = totalHeight / colCount;
		this.padding = padding;
	}

	public Rect calculateDimensions(Point topLeftCell, Point bottomRightCell)
	{
		Point newTopLeft = new Point();
		newTopLeft.x = topLeftCell.x * cellWidth + padding;
		newTopLeft.y = topLeftCell.y * cellHeight + padding;

		Point newBottomRight = new Point();
		newBottomRight.x = (bottomRightCell.x + 1) * cellWidth - padding;
		newBottomRight.y = (bottomRightCell.y + 1) * cellHeight - padding;

		return new Rect(newTopLeft, newBottomRight);
	}
}

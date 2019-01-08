package io.github.jakejmattson.touchcontrol.utils

import org.opencv.core.*

class Grid(rowCount: Int, colCount: Int, totalWidth: Double, totalHeight: Double, private val padding: Int) {
    private val cellWidth: Double = totalWidth / rowCount
    private val cellHeight: Double = totalHeight / colCount

    fun calculateDimensions(topLeftCell: Point, bottomRightCell: Point): Rect {
        val newTopLeft = Point()
        newTopLeft.x = topLeftCell.x * cellWidth + padding
        newTopLeft.y = topLeftCell.y * cellHeight + padding

        val newBottomRight = Point()
        newBottomRight.x = (bottomRightCell.x + 1) * cellWidth - padding
        newBottomRight.y = (bottomRightCell.y + 1) * cellHeight - padding

        return Rect(newTopLeft, newBottomRight)
    }
}
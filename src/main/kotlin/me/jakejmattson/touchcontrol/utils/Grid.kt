package me.jakejmattson.touchcontrol.utils

import org.opencv.core.*

class Grid(rowCount: Int, colCount: Int, totalWidth: Double, totalHeight: Double, private val padding: Int) {
    private val dimensionPoint = Point(totalWidth / rowCount, totalHeight / colCount)

    fun calculateDimensions(topLeftCell: Point, bottomRightCell: Point): Rect {
        val newTopLeft = topLeftCell * dimensionPoint + padding
        val newBottomRight = (bottomRightCell + 1) * dimensionPoint - padding

        return Rect(newTopLeft, newBottomRight)
    }
}
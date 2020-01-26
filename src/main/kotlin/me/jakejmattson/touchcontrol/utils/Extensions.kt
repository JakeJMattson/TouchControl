package me.jakejmattson.touchcontrol.utils

import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import java.awt.Robot
import java.awt.image.BufferedImage

operator fun Point.plus(b: Int) = Point(x + b, y + b)
operator fun Point.minus(b: Int) = Point(x - b, y - b)
operator fun Point.times(b: Point) = Point(x * b.x, y * b.y)
operator fun Point.minus(b: Point) = Point(x - b.x, y - b.y)

fun Robot.mouseMove(point: Point) = mouseMove(point.x.toInt(), point.y.toInt())

fun Mat.toBufferedImage(): BufferedImage {
    val type = if (this.channels() != 1) BufferedImage.TYPE_3BYTE_BGR else BufferedImage.TYPE_BYTE_GRAY

    if (type == BufferedImage.TYPE_3BYTE_BGR)
        Imgproc.cvtColor(this, this, Imgproc.COLOR_BGR2RGB)

    //Get matrix data
    val width = this.width()
    val height = this.height()
    val data = ByteArray(width * height * this.elemSize().toInt())
    this.get(0, 0, data)

    //Create image with matrix data
    val out = BufferedImage(width, height, type)
    out.raster.setDataElements(0, 0, width, height, data)

    return out
}
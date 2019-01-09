package io.github.jakejmattson.touchcontrol.utils

import org.opencv.core.Point

operator fun Point.plus(b: Int) = Point(x + b, y + b)
operator fun Point.minus(b: Int) = Point(x - b, y - b)
operator fun Point.times(b: Point) = Point(x * b.x, y * b.y)
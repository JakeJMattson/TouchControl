@file:Suppress("unused")

package me.jakejmattson.touchcontrol.demo

import me.jakejmattson.touchcontrol.touchables.*
import me.jakejmattson.touchcontrol.utils.*
import org.opencv.core.*

private const val PADDING = 10

fun createMouseDemo(cameraWidth: Double, cameraHeight: Double, color: Scalar): TouchableGroup {
    val grid = Grid(1, 1, cameraWidth, cameraHeight, PADDING)
    val dimensions = grid.calculateDimensions(Point(0.0, 0.0), Point(0.0, 0.0))

    return TouchableGroup(MousePad(dimensions, color))
}

fun createPianoDemo(cameraWidth: Double, cameraHeight: Double, color: Scalar): TouchableGroup {
    val keyCount = 7
    val group = TouchableGroup()
    val grid = Grid(keyCount, 5, cameraWidth, cameraHeight, PADDING)

    for (i in 0 until keyCount) {
        val dimensions = grid.calculateDimensions(Point(i.toDouble(), 1.0), Point(i.toDouble(), 3.0))
        val key = PianoKey(dimensions, color, (i + 'A'.toInt()).toChar())
        group.addComponent(key)
    }

    return group
}

fun createVolumeDemo(cameraWidth: Double, cameraHeight: Double, color: Scalar): TouchableGroup {
    val grid = Grid(100, 1, cameraWidth, cameraHeight, PADDING)
    val dimensions = grid.calculateDimensions(Point(40.0, 0.0), Point(60.0, 0.0))

    return TouchableGroup(VolumeSlider(dimensions, color))
}
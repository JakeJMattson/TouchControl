package io.github.jakejmattson.touchcontrol.touchables

import org.opencv.core.*

/**
 * Abstract Pad - contains general pad behavior.
 *
 * @author JakeJMattson
 */
abstract class Pad protected constructor(dimensions: Rect, color: Scalar): Touchable(dimensions, color)
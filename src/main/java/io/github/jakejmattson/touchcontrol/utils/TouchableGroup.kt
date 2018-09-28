package io.github.jakejmattson.touchcontrol.utils

import io.github.jakejmattson.touchcontrol.touchables.Touchable
import org.opencv.core.*
import java.util.*

/**
 * Apply the same function over many Touchable objects.
 *
 * @author JakeJMattson
 */
class TouchableGroup(vararg components: Touchable) {
	/**
	 * A List of all components in the group
	 */
	private val components: ArrayList<Touchable> = ArrayList(Arrays.asList(*components))

	fun addComponent(component: Touchable) = components.add(component)

	fun removeComponent(component: Touchable) = components.remove(component)

	fun getComponents() = components

	fun setColor(color: Scalar) = components.forEach { it.color = color }

	fun updateDetectionPoint(filteredImage: Mat) = components.forEach { it.updateDetectionPoint(filteredImage) }

	fun drawOnto(rawImage: Mat) = components.forEach { it.drawOnto(rawImage) }

	fun performAction() = components.forEach { it.performAction() }

	/**
	 * Check whether or not any of the components in
	 * the group overlaps (collides) with another.
	 * Note: This has no return. It is only to print warnings.
	 */
	private fun checkCollision() {
		for (i in components.indices)
			for (j in i + 1 until components.size) {
				//Get components dimensions
				val dim1 = components[i].dimensions
				val dim2 = components[j].dimensions

				//Check if two components overlap
				val overlaps = !(dim1.y + dim1.height <= dim2.y
						|| dim1.y >= dim2.y + dim2.height
						|| dim1.x + dim1.width <= dim2.x
						|| dim1.x >= dim2.x + dim2.width)

				//Print warning
				if (overlaps)
					println("Touchable collision warning in " + super.toString() +
							" between component " + i + " and component " + j)
			}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	override fun toString(): String {
		val newline = System.lineSeparator()
		val groupData = StringBuilder()

		groupData.append("Touchable objects in group (").append(super.toString()).append("): ")
			.append(components.size).append(newline).append(newline)

		for (i in components.indices)
			groupData.append("(").append(i).append(")").append(components[i].toString()).append(newline)

		return groupData.toString()
	}
}
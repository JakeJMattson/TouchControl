package io.github.jakejmattson.touchcontrol.utils

import io.github.jakejmattson.touchcontrol.touchables.Touchable
import kotlinx.coroutines.*
import org.opencv.core.*
import java.util.*
import kotlin.collections.ArrayList

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

	fun updateDetectionPoint(filteredImage: Mat) =
		runBlocking {
			val jobs = ArrayList<Job>()

			components.forEach {
				jobs.add(
					GlobalScope.launch { it.updateDetectionPoint(filteredImage) }
				)
			}

			jobs.joinAll()
		}

	fun drawOnto(rawImage: Mat) =
		runBlocking {
			val jobs = ArrayList<Job>()

			components.forEach {
				jobs.add(
					GlobalScope.launch { it.drawOnto(rawImage) }
				)
			}

			jobs.joinAll()
		}

	fun performAction() =
		runBlocking {
			val jobs = ArrayList<Job>()

			components.forEach {
				jobs.add(
					GlobalScope.launch { it.performAction() }
				)
			}

			jobs.joinAll()
		}

	/**
	 * Check whether or not any of the components in
	 * the group overlaps (collides) with another.
	 *
	 * @return Whether or not the group is collision-free
	 */
	private fun checkCollision(): Boolean {

		var hasNoCollision = true

		for (i in components.indices)
			for (j in i + 1 until components.size) {

				val dim1 = components[i].dimensions
				val dim2 = components[j].dimensions

				//Check if two components overlap
				val overlaps = !(dim1.y + dim1.height <= dim2.y
						|| dim1.y >= dim2.y + dim2.height
						|| dim1.x + dim1.width <= dim2.x
						|| dim1.x >= dim2.x + dim2.width)

				if (overlaps) {
					hasNoCollision = false
					
					println("Touchable collision warning in ${super.toString()} between component $i and component $j")
				}
			}

		return hasNoCollision
	}

	override fun toString(): String {
		var groupData = "Touchable objects in group (${super.toString()}): ${components.size}\n\n"

		components.forEachIndexed { index, component ->
			groupData += "($index)$component\n"
		}

		return groupData
	}
}
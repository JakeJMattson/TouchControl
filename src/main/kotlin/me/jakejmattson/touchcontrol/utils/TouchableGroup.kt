package me.jakejmattson.touchcontrol.utils

import kotlinx.coroutines.*
import me.jakejmattson.touchcontrol.touchables.Touchable
import org.opencv.core.*
import java.util.Arrays

/**
 * Apply the same function over many Touchable objects.
 *
 * @author JakeJMattson
 */
class TouchableGroup(vararg components: Touchable) {

    private val components: ArrayList<Touchable> = arrayListOf(*components)

    fun addComponent(component: Touchable) = components.add(component)

    fun removeComponent(component: Touchable) = components.remove(component)

    fun getComponents() = components

    fun setColor(color: Scalar) = components.forEach { it.color = color }

    fun updateDetectionPoint(filteredImage: Mat) =
        runBlocking {
            components.forEach {
                launch { it.updateDetectionPoint(filteredImage) }
            }
        }

    fun drawOnto(rawImage: Mat) =
        runBlocking {
            components.forEach {
                launch { it.drawOnto(rawImage) }
            }
        }

    fun performAction() =
        runBlocking {
            components.forEach {
                launch { it.performAction() }
            }
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

    override fun toString() =
        buildString {
            appendln("Touchable objects in group (${super.toString()}): ${components.size}\n")

            components.forEachIndexed { index, component ->
                appendln("($index)$component")
            }
        }
}
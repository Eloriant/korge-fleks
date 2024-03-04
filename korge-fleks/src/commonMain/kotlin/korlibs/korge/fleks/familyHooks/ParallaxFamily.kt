package korlibs.korge.fleks.familyHooks

import com.github.quillraven.fleks.Family
import com.github.quillraven.fleks.FamilyHook
import com.github.quillraven.fleks.World
import korlibs.korge.fleks.components.MotionComponent
import korlibs.korge.fleks.components.ParallaxComponent

fun parallaxFamily(): Family = World.family { all(ParallaxComponent).any(ParallaxComponent, MotionComponent) }

val onParallaxFamilyAdded: FamilyHook = { entity ->
}

val onParallaxFamilyRemoved: FamilyHook = { entity ->
}

package korlibs.korge.fleks.components

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.configureWorld
import korlibs.math.interpolation.Easing
import korlibs.korge.fleks.entity.config.Invokable
import korlibs.korge.fleks.utils.Identifier
import kotlin.test.Test
import kotlin.test.assertEquals


internal class AnimationScriptTest {

    private val expectedWorld = configureWorld {}
    private val recreatedWorld = configureWorld {}

    @Test
    fun testAnimationScriptSerialization() {

        Invokable.register(testConfigure, testConfigureFct)

        val testIdentifier = Identifier("testEntityConfig")

        val compUnderTest = TweenScript(
            tweenSequence = listOf(
                Sequence(
                    tweens = listOf(
                        Parallel(
                            tweens = listOf(
                                SpawnEntity(
                                    config = testIdentifier,
                                    function = testConfigure,
                                    x = 10.2f,
                                    y = 20.3f,
                                    entity = Entity(43, 0u)
                                ),
                                DeleteEntity(entity = Entity(44, 0u)),
                                TweenAppearance(entity = Entity(45, 0u)),
                                TweenPositionShape(entity = Entity(46, 0u)),
                                TweenOffset(entity = Entity(47, 0u)),
                                TweenSprite(entity = Entity(48, 0u)),
                                TweenSwitchLayerVisibility(entity = Entity(49, 0u)),
                                TweenSpawner(entity = Entity(51, 0u)),
                                TweenSound(entity = Entity(52, 0u))
                            ),
                            delay = 1.2f,
                            duration = 3.4f,
                            easing = Easing.EASE_CLAMP_END
                        )
                    ),
                    delay = 1f,
                    duration = 2f,
                    easing = Easing.EASE_IN_BACK
                ),
                Wait(duration = 3.2f)
            ),
            index = 42,
            timeProgress = 3.4f,
            waitTime = 5.6f,
            active = true
        )

        val entity = expectedWorld.entity {
            it += compUnderTest
        }

        CommonTestEnv.serializeDeserialize(expectedWorld, recreatedWorld)

        // get the component from entity with the same id from the new created world
        val newEntity = recreatedWorld.asEntityBag()[entity.id]
        val newCompUnderTest = with (recreatedWorld) { newEntity[TweenScript] }

        assertEquals(compUnderTest.index, newCompUnderTest.index, "Check 'index' property to be equal")
        assertEquals(compUnderTest.timeProgress, newCompUnderTest.timeProgress, "Check 'timeProgress' property to be equal")
        assertEquals(compUnderTest.waitTime, newCompUnderTest.waitTime, "Check 'waitTime' property to be equal")
        assertEquals(compUnderTest.active, newCompUnderTest.active, "Check 'active' property to be equal")
        val sequenceOfTweens = compUnderTest.tweenSequence.first() as Sequence
        val newSequenceOfTweens = compUnderTest.tweenSequence.first() as Sequence
        assertEquals(sequenceOfTweens.delay, newSequenceOfTweens.delay, "Check 'tweenSequence.delay' property to be equal")
        assertEquals(sequenceOfTweens.duration, newSequenceOfTweens.duration, "Check 'tweenSequence.duration' property to be equal")
        assertEquals(sequenceOfTweens.easing, newSequenceOfTweens.easing, "Check 'tweenSequence.easing' property to be equal")
        val parallelTweens = sequenceOfTweens.tweens.first() as Parallel
        val newParallelTweens = newSequenceOfTweens.tweens.first() as Parallel
        assertEquals(parallelTweens.delay, newParallelTweens.delay, "Check 'parallelTweens.delay' property to be equal")
        assertEquals(parallelTweens.duration, newParallelTweens.duration, "Check 'parallelTweens.duration' property to be equal")
        assertEquals(parallelTweens.easing, newParallelTweens.easing, "Check 'parallelTweens.easing' property to be equal")
        val spawnEntity = parallelTweens.tweens.first() as SpawnEntity
        val newSpawnEntity = newParallelTweens.tweens.first() as SpawnEntity
        assertEquals(spawnEntity.config, newSpawnEntity.config, "spawnEntity.config' property to be equal")
        assertEquals(spawnEntity.function, newSpawnEntity.function, "Check 'spawnEntity.function' property to be equal")
        assertEquals(spawnEntity.x, newSpawnEntity.x, "Check 'spawnEntity.x' property to be equal")
        assertEquals(spawnEntity.y, newSpawnEntity.y, "Check 'spawnEntity.y' property to be equal")
        assertEquals(spawnEntity.entity, newSpawnEntity.entity, "Check 'spawnEntity.entity' property to be equal")
        val spawnedEntity = Invokable.invoke(spawnEntity.function, recreatedWorld, Entity(4242, 0u), spawnEntity.config)
        assertEquals(spawnedEntity.id, 8080, "Check that configure function is invoked correctly")
    }
}

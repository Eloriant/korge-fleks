package korlibs.korge.fleks.components

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Currently this is used to get the layout details for an Entity from the asset object.
 * The asset object is configured from GameModel.
 */
@Serializable
@SerialName("Layout")
data class LayoutComponent(
    var centerX: Boolean = false,
    var centerY: Boolean = false,
    var offsetX: Double = 0.0,
    var offsetY: Double = 0.0
) : Component<LayoutComponent> {
    override fun type(): ComponentType<LayoutComponent> = LayoutComponent
    companion object : ComponentType<LayoutComponent>()
}

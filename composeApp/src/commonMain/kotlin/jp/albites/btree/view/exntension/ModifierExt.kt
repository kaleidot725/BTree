package jp.albites.btree.view.exntension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role

fun Modifier.clickableNoRipple(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
): Modifier {
    return this.composed {
        this.clickable(
            enabled = enabled,
            onClickLabel = onClickLabel,
            role = role,
            onClick = onClick,
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
        )
    }
}

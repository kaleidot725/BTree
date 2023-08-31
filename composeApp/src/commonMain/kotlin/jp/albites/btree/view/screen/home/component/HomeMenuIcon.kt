package jp.albites.btree.view.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.albites.btree.view.exntension.clickableNoRipple

@Composable
fun HomeMenuIcon(
    icon: ImageVector,
    label: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickableNoRipple(enabled = enabled, onClick = onClick)
                .align(Alignment.Center),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = getColor(enabled),
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterHorizontally),
            )
            Text(
                text = label,
                fontSize = 10.sp,
                color = getColor(enabled),
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }
    }
}

@Composable
private fun getColor(enabled: Boolean): Color {
    return if (enabled) {
        LocalContentColor.current
    } else {
        LocalContentColor.current.copy(alpha = 0.38f)
    }
}

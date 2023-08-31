package jp.albites.btree.view.screen.setting.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
internal fun SettingItem(
    title: String,
    icon: ImageVector,
    iconDescription: String,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconDescription,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterVertically),
        )

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1.0f)
                .align(Alignment.CenterVertically),
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

package view.components.explorer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.albites.btree.explorer.filetree.File

@Composable
fun DirectoryItem(
    file: File,
    expanded: Boolean,
    onExpand: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        val degrees = if (expanded) 90f else 0f
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .rotate(degrees)
                .align(Alignment.CenterVertically)
                .clickable { onExpand.invoke() }
        )

        Icon(
            imageVector = Icons.Default.AccountBox,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )

        Text(
            text = file.name,
            maxLines = 1,
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Visible,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}
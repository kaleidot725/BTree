package view.components.explorer

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import jp.albites.btree.explorer.filetree.File

@Composable
fun DirectoryItem(
    file: File,
    expanded: Boolean,
    onExpand: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        val degrees: Float by animateFloatAsState(if (expanded) 90f else 0f)
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .rotate(degrees)
                .align(Alignment.CenterVertically)
                .clip(CircleShape)
                .clickable(role = Role.Checkbox) { onExpand.invoke() }
        )

        Icon(
            imageVector = Icons.Default.Folder,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = file.name,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            overflow = TextOverflow.Visible,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}
package view.components.explorer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.albites.btree.explorer.filetree.File

@Composable
fun FileItem(
    file: File,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Spacer(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterVertically)
        )

        Icon(
            imageVector = Icons.Default.Bookmark,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = file.name,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            overflow = TextOverflow.Visible,
            modifier = Modifier
                .weight(1f, fill = true)
                .align(Alignment.CenterVertically)
        )
    }
}
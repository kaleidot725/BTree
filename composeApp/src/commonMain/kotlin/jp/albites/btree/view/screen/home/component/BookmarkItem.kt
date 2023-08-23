package jp.albites.btree.view.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookOnline
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import jp.albites.btree.model.domain.Bookmark

@Composable
internal fun BookmarkItem(
    bookmark: Bookmark,
    openBookmark: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Icon(
            imageVector = Icons.Default.Bookmark,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = bookmark.name,
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium,
            overflow = TextOverflow.Visible,
            modifier = Modifier
                .weight(1f, fill = true)
                .align(Alignment.CenterVertically)
        )

        Icon(
            imageVector = Icons.Default.OpenInBrowser,
            contentDescription = null,
            modifier = Modifier
                .width(width = 40.dp)
                .background(
                    color = Color.LightGray.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(shape = RoundedCornerShape(8.dp))
                .clickable { openBookmark() }
        )
    }
}
package jp.albites.btree.view.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import jp.albites.btree.model.domain.Bookmark
import jp.albites.btree.model.domain.Directory
import jp.albites.btree.model.domain.File

@Composable
internal fun DirectoryTree(
    directory: Directory,
    selectedFile: File,
    expandedDirs: List<Directory>,
    level: Int = 0,
    onClickArrow: (Directory) -> Unit = {},
    onClickFile: (File) -> Unit = { },
    onClickBookmark: (Bookmark) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val isExpanded by rememberUpdatedState(expandedDirs.any { it.id == directory.id })

    Column(modifier = modifier) {
        DirectoryItem(
            directory = directory,
            isExpanded = isExpanded,
            onExpand = {
                onClickArrow.invoke(directory)
                onClickFile.invoke(directory)
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickFile.invoke(directory) }
                .background(if (selectedFile.id == directory.id) BottomAppBarDefaults.bottomAppBarFabColor else Color.Transparent)
                .padding(horizontal = 8.dp)
                .padding(vertical = 4.dp)
                .padding(start = level * 24.dp),
        )

        if (isExpanded) {
            directory.list.forEach {
                when (it) {
                    is Bookmark -> {
                        BookmarkItem(
                            bookmark = it,
                            openBookmark = { onClickBookmark(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onClickFile.invoke(it) }
                                .background(if (selectedFile.id == it.id) BottomAppBarDefaults.bottomAppBarFabColor else Color.Transparent)
                                .padding(horizontal = 8.dp)
                                .padding(vertical = 4.dp)
                                .padding(start = (level + 1) * 24.dp),
                        )
                    }

                    is Directory -> {
                        DirectoryTree(
                            directory = it,
                            selectedFile = selectedFile,
                            expandedDirs = expandedDirs,
                            level = level + 1,
                            onClickFile = onClickFile,
                            onClickArrow = onClickArrow,
                            onClickBookmark = { onClickBookmark(it) },
                        )
                    }
                }
            }
        }
    }
}

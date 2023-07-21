package view.components.explorer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import jp.albites.btree.component.explorer.Bookmark
import jp.albites.btree.component.explorer.BookmarkItem
import jp.albites.btree.component.explorer.Directory
import jp.albites.btree.component.explorer.DirectoryTree
import jp.albites.btree.component.explorer.ExplorerTitle
import jp.albites.btree.component.explorer.File

@Composable
fun Explorer(
    title: String,
    targetFile: File,
    selectedFile: File,
    expandedDirs: List<Directory>,
    onClickHome: () -> Unit,
    onClickArrow: (Directory) -> Unit,
    onClickFile: (File) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        ExplorerTitle(
            title = title,
            onOpen = onClickHome,
            modifier = Modifier.padding(8.dp)
        )

        when (targetFile) {
            is Bookmark -> {
                BookmarkItem(
                    bookmark = targetFile,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClickFile.invoke(targetFile) }
                        .background(if (selectedFile == targetFile) BottomAppBarDefaults.bottomAppBarFabColor else Color.Transparent)
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 4.dp)
                )
            }

            is Directory -> {
                DirectoryTree(
                    directory = targetFile,
                    selectedFile = selectedFile,
                    expandedDirs = expandedDirs,
                    level = 0,
                    onClickFile = onClickFile,
                    onClickArrow = onClickArrow
                )
            }
        }
    }
}
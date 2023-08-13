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
import jp.albites.btree.model.domain.Bookmark
import jp.albites.btree.view.screen.home.component.BookmarkItem
import jp.albites.btree.model.domain.Directory
import jp.albites.btree.view.screen.home.component.DirectoryTree
import jp.albites.btree.model.domain.File

@Composable
fun Explorer(
    targetFile: File,
    selectedFile: File,
    expandedDirs: List<Directory>,
    onClickArrow: (Directory) -> Unit,
    onClickFile: (File) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        when (targetFile) {
            is Bookmark -> {
                BookmarkItem(
                    bookmark = targetFile,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClickFile.invoke(targetFile) }
                        .background(if (selectedFile.id == targetFile.id) BottomAppBarDefaults.bottomAppBarFabColor else Color.Transparent)
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
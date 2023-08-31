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
import jp.albites.btree.model.domain.Directory
import jp.albites.btree.model.domain.File
import jp.albites.btree.view.screen.home.component.BookmarkItem
import jp.albites.btree.view.screen.home.component.DirectoryTree

@Composable
fun Explorer(
    rootDirectory: Directory,
    selectedFile: File,
    expandedDirs: List<Directory>,
    onClickArrow: (Directory) -> Unit,
    onClickFile: (File) -> Unit,
    onClickBookmark: (Bookmark) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        rootDirectory.list.forEach { file ->
            when (file) {
                is Bookmark -> {
                    BookmarkItem(
                        bookmark = file,
                        openBookmark = { onClickBookmark.invoke(file) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onClickFile.invoke(file) }
                            .background(if (selectedFile.id == file.id) BottomAppBarDefaults.bottomAppBarFabColor else Color.Transparent)
                            .padding(horizontal = 8.dp)
                            .padding(vertical = 4.dp),
                    )
                }

                is Directory -> {
                    DirectoryTree(
                        directory = file,
                        selectedFile = selectedFile,
                        expandedDirs = expandedDirs,
                        level = 0,
                        onClickFile = onClickFile,
                        onClickArrow = onClickArrow,
                        onClickBookmark = onClickBookmark,
                    )
                }
            }
        }
    }
}

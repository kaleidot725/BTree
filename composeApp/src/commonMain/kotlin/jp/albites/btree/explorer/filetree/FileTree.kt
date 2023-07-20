package view.components.explorer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import jp.albites.btree.explorer.filetree.File

@Composable
fun FileTree(
    file: File,
    selectedFile: File,
    level: Int = 0,
    onClickFile: (File) -> Unit = { },
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        DirectoryItem(
            file = file,
            expanded = expanded,
            onExpand = {
                expanded = !expanded
                onClickFile.invoke(file)
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickFile.invoke(file) }
                .background(if (selectedFile == file) BottomAppBarDefaults.bottomAppBarFabColor else Color.Transparent)
                .padding(horizontal = 8.dp)
                .padding(vertical = 4.dp)
                .padding(start = level * 24.dp)
        )

        if (expanded) {
            file.listFiles.forEach {
                if (it.isDirectory) {
                    FileTree(
                        file = it,
                        selectedFile = selectedFile,
                        level = level + 1,
                        onClickFile = onClickFile
                    )
                } else {
                    FileItem(
                        file = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onClickFile.invoke(it) }
                            .background(if (selectedFile == it) BottomAppBarDefaults.bottomAppBarFabColor else Color.Transparent)
                            .padding(horizontal = 8.dp)
                            .padding(vertical = 4.dp)
                            .padding(start = (level + 1) * 24.dp)
                    )
                }
            }
        }
    }
}



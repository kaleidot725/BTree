package view.components.explorer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import jp.albites.btree.explorer.filetree.File

@Composable
fun FileTree(
    file: File = File(),
    level: Int = 0,
    onClickFile: (File) -> Unit = { },
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        DirectoryItem(
            file = file,
            expanded = expanded,
            onExpand = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(horizontal = 8.dp)
                .padding(vertical = 4.dp)
                .padding(start = level * 20.dp)
        )

        if (expanded) {
            file.listFiles.forEach {
                if (it.isDirectory) {
                    FileTree(
                        file = it,
                        level = level + 1,
                        onClickFile = onClickFile
                    )
                } else {
                    FileItem(
                        file = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onClickFile.invoke(it) }
                            .padding(horizontal = 8.dp)
                            .padding(vertical = 4.dp)
                            .padding(start = (level + 1) * 20.dp)
                    )
                }
            }
        }
    }
}



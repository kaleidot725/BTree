package view.components.explorer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.albites.btree.explorer.filetree.File

@Composable
fun Explorer(
    title: String,
    targetDirectory: File,
    onClickHome: () -> Unit,
    onClickFile: (File) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        FileTitle(
            title = title,
            onOpen = onClickHome,
            modifier = Modifier.padding(8.dp)
        )

        FileTree(
            file = targetDirectory,
            level = 0,
            onClickFile = { onClickFile.invoke(it) },
            modifier = Modifier.verticalScroll(rememberScrollState())
        )
    }
}
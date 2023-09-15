package jp.albites.btree.view.screen.home.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.lyricist.LocalStrings
import cafe.adriel.voyager.core.screen.Screen
import jp.albites.btree.model.domain.Directory
import jp.albites.btree.util.getDialogModel
import org.koin.core.parameter.parametersOf

@Composable
fun Screen.BookmarkDialog(
    targetDirectory: Directory,
    onClose: () -> Unit,
    onApply: () -> Unit,
) {
    val screenModel = getDialogModel<BookmarkDialogModel>(tag = targetDirectory.toString()) {
        (parametersOf(targetDirectory.id))
    }
    val state by screenModel.state.collectAsState()
    var name by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    val strings = LocalStrings.current

    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(),
    ) {
        Card(
            modifier = Modifier
                .wrapContentSize()
                .windowInsetsPadding(WindowInsets.ime),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp),
            ) {
                Text(
                    text = strings.bookmarkTitle,
                    style = MaterialTheme.typography.titleMedium,
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        screenModel.updateName(it)
                        name = it
                    },
                    placeholder = { Text(strings.bookmarkNamePlaceHolder) },
                )

                OutlinedTextField(
                    value = url,
                    onValueChange = {
                        screenModel.updateUrl(it)
                        url = it
                    },
                    placeholder = { Text(strings.bookmarkUrlPlaceHolder) },
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                ) {
                    Button(
                        onClick = { onClose() },
                    ) {
                        Text(strings.close)
                    }

                    Button(
                        enabled = state.isValid,
                        onClick = {
                            screenModel.register()
                            onApply()
                        },
                    ) {
                        Text(strings.apply)
                    }
                }
            }
        }
    }
}

package jp.albites.btree.view.screen.home.delete

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.lyricist.LocalStrings
import cafe.adriel.voyager.core.screen.Screen
import jp.albites.btree.model.domain.File
import jp.albites.btree.util.getDialogModel
import org.koin.core.parameter.parametersOf

@Composable
fun Screen.DeleteDialog(
    targetFile: File,
    onClose: () -> Unit,
    onApply: () -> Unit,
) {
    val screenModel = getDialogModel<DeleteDialogModel>(tag = targetFile.toString()) {
        (parametersOf(targetFile.id))
    }
    val state by screenModel.state.collectAsState()
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
                Column {
                    Text(
                        text = strings.deleteTitle,
                        style = MaterialTheme.typography.titleMedium,
                    )

                    Text(
                        text = strings.deleteMessage(state.file.name),
                        style = MaterialTheme.typography.titleSmall,
                    )
                }

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
                        onClick = {
                            screenModel.delete()
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

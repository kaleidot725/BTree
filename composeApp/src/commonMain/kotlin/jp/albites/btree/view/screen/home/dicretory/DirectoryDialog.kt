package jp.albites.btree.view.screen.home.dicretory

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.core.screen.Screen
import jp.albites.btree.model.domain.Directory
import jp.albites.btree.util.getDialogModel
import jp.albites.btree.view.resources.StringResource
import org.koin.core.parameter.parametersOf

@Composable
fun Screen.DirectoryDialog(
    targetDirectory: Directory,
    onClose: () -> Unit,
    onApply: () -> Unit,
) {
    val screenModel = getDialogModel<DirectoryDialogModel>(tag = targetDirectory.toString()) {
        (parametersOf(targetDirectory.id))
    }
    val state by screenModel.state.collectAsState()

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
                    text = StringResource.directoryTitle(),
                    style = MaterialTheme.typography.titleMedium,
                )

                OutlinedTextField(
                    value = state.name,
                    onValueChange = screenModel::updateName,
                    placeholder = { Text(StringResource.directoryNamePlaceHolder()) },
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                ) {
                    Button(
                        onClick = { onClose() },
                    ) {
                        Text(StringResource.close())
                    }

                    Button(
                        enabled = state.isValid,
                        onClick = {
                            screenModel.register()
                            onApply()
                        },
                    ) {
                        Text(StringResource.apply())
                    }
                }
            }
        }
    }
}

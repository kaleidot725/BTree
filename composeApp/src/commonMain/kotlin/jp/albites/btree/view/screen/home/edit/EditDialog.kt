package jp.albites.btree.view.screen.home.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import jp.albites.btree.model.domain.File
import jp.albites.btree.util.getDialogModel
import org.koin.core.parameter.parametersOf

@Composable
fun Screen.EditDialog(
    targetFile: File,
    onClose: () -> Unit,    
    onApply: () -> Unit,
) {
    val screenModel = getDialogModel<EditDialogModel>(tag = targetFile.toString()) {
        (parametersOf(targetFile.id))
    }
    val state by screenModel.state.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize().background(Color.DarkGray.copy(alpha = 0.2f))
    ) {
        Card(
            modifier = Modifier.align(Alignment.Center).wrapContentSize()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Edit",
                    style = MaterialTheme.typography.titleMedium
                )

                OutlinedTextField(
                    value = state.name,
                    onValueChange = { screenModel.updateName(it) },
                    placeholder = { Text("Name") }
                )

                if (state.file.isBookmark) {
                    OutlinedTextField(
                        value = state.url,
                        onValueChange = { screenModel.updateUrl(it) },
                        placeholder = { Text("URL") }
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Button(
                        onClick = { onClose() },
                    ) {
                        Text("Close")
                    }

                    Button(
                        onClick = {
                            screenModel.apply()
                            onApply()
                        },
                        enabled = state.isValid
                    ) {
                        Text("Apply")
                    }
                }
            }
        }
    }
}
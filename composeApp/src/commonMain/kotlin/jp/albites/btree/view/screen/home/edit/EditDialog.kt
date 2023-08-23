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
import jp.albites.btree.getScreenModel
import jp.albites.btree.model.domain.File
import org.koin.core.parameter.parametersOf

@Composable
fun Screen.EditDialog(
    targetFile: File,
    onClose: () -> Unit,
    onApply: () -> Unit,
) {
    val screenModel = getScreenModel<EditDialogModel> {
        (parametersOf(targetFile))
    }
    val name by screenModel.name.collectAsState()
    val url by screenModel.url.collectAsState()
    val isValid by screenModel.isValid.collectAsState()

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
                    value = name,
                    onValueChange = { screenModel.updateName(it) },
                    placeholder = { Text("Name") }
                )

                if (screenModel.isBookmark) {
                    OutlinedTextField(
                        value = url,
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
                        enabled = isValid
                    ) {
                        Text("Apply")
                    }
                }
            }
        }
    }
}
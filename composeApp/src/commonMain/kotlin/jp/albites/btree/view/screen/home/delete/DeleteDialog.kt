package jp.albites.btree.view.screen.home.delete

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import jp.albites.btree.getDialogScreenModel
import jp.albites.btree.model.domain.Directory
import jp.albites.btree.model.domain.File
import org.koin.core.parameter.parametersOf

@Composable
fun Screen.DeleteDialog(
    targetFile: File,
    onClose: () -> Unit,
    onApply: () -> Unit,
) {
    val screenModel = getDialogScreenModel<DeleteDialogModel>(tag = targetFile.toString()) {
        (parametersOf(targetFile))
    }

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
                Column {
                    Text(
                        text = "Delete",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Do you delete bookmark?",
                        style = MaterialTheme.typography.titleSmall
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
                            screenModel.delete()
                            onApply()
                        },
                    ) {
                        Text("Apply")
                    }
                }

            }
        }
    }
}
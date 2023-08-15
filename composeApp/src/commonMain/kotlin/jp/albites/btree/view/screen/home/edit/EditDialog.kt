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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EditDialog(
    onClose: () -> Unit,
    onApply: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.DarkGray.copy(alpha = 0.2f))
    ) {
        Card(
            modifier = Modifier.align(Alignment.Center).wrapContentSize()
        ) {
            var name by remember { mutableStateOf("") }
            var url by remember { mutableStateOf("") }

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
                    onValueChange = { name = it },
                    placeholder = { Text("Name") }
                )

                OutlinedTextField(
                    value = url,
                    onValueChange = { url = it },
                    placeholder = { Text("URL") }
                )

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
                        onClick = { onApply() },
                    ) {
                        Text("Apply")
                    }
                }
            }
        }
    }
}
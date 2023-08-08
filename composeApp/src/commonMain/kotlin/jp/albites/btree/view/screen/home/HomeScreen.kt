package jp.albites.btree.view.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AddLink
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import jp.albites.btree.view.screen.setting.SettingScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import view.components.explorer.Explorer

class HomeScreen(val openUrl: (String) -> Unit) : Screen {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { HomeScreenModel() }
        val fileTree by screenModel.fileTree.collectAsState()
        val selectedFile by screenModel.selectedFile.collectAsState()
        val expandedDirs by screenModel.expandedDirs.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        var showAddDialog by remember { mutableStateOf(false) }
        var showDeleteDialog by remember { mutableStateOf(false) }
        var showEditDialog by remember { mutableStateOf(false) }

        Box {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("BTree") },
                        actions = {
                            IconButton(onClick = { navigator.push(SettingScreen()) }) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                },
                bottomBar = {
                    BottomAppBar(
                        actions = {
                            AnimatedVisibility(
                                visible = true,
                                enter = scaleIn(),
                                exit = scaleOut()
                            ) {
                                IconButton(onClick = { showDeleteDialog = true }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                            AnimatedVisibility(
                                visible = true,
                                enter = scaleIn(),
                                exit = scaleOut()
                            ) {
                                IconButton(onClick = { showEditDialog = true }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit",
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                            AnimatedVisibility(
                                visible = true,
                                enter = scaleIn(),
                                exit = scaleOut()
                            ) {
                                IconButton(
                                    onClick = { showAddDialog = true }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AddCircle,
                                        contentDescription = "AddLink",
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        },
                        floatingActionButton = {
                            AnimatedVisibility(
                                visible = selectedFile.isBookmark,
                                enter = scaleIn(),
                                exit = scaleOut()
                            ) {
                                FloatingActionButton(
                                    onClick = {
                                        selectedFile.asBookmark?.let { openUrl(it.url) }
                                    },
                                    containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                                ) {
                                    Icon(
                                        painter = painterResource("browser.png"),
                                        contentDescription = "Open in browser",
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    )
                },
            ) {
                Explorer(
                    targetFile = fileTree,
                    selectedFile = selectedFile,
                    expandedDirs = expandedDirs,
                    onClickFile = { file -> screenModel.onClickFile(file) },
                    onClickArrow = { directory -> screenModel.onClickArrow(directory) },
                    modifier = Modifier.fillMaxSize().padding(it)
                )
            }

            if (showAddDialog) {
                AddScreen(
                    onApply = { showAddDialog = false },
                    onClose = { showAddDialog = false }
                )
            }

            if (showDeleteDialog) {
                DeleteScreen(
                    onApply = { showDeleteDialog = false },
                    onClose = { showDeleteDialog = false }
                )
            }

            if (showEditDialog) {
                EditScreen(
                    onApply = { showEditDialog = false },
                    onClose = { showEditDialog = false }
                )
            }
        }
    }
}

@Composable
private fun AddScreen(
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
                    text = "Register",
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

@Composable
private fun DeleteScreen(
    onClose: () -> Unit,
    onApply: () -> Unit,
) {
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
                        onClick = { onApply() },
                    ) {
                        Text("Apply")
                    }
                }

            }
        }
    }
}


@Composable
private fun EditScreen(
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
package jp.albites.btree.view.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLink
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import jp.albites.btree.getScreenModel
import jp.albites.btree.view.screen.home.bookmark.BookmarkDialog
import jp.albites.btree.view.screen.home.delete.DeleteDialog
import jp.albites.btree.view.screen.home.dicretory.DirectoryDialog
import jp.albites.btree.view.screen.home.edit.EditDialog
import jp.albites.btree.view.screen.setting.SettingScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import view.components.explorer.Explorer

class HomeScreen(val openUrl: (String) -> Unit) : Screen {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<HomeScreenModel>()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        var showBookmarkDialog by remember { mutableStateOf(false) }
        var showDirectoryDialog by remember { mutableStateOf(false) }
        var showDeleteDialog by remember { mutableStateOf(false) }
        var showEditDialog by remember { mutableStateOf(false) }

        val selectedFile by rememberUpdatedState(state.selectedFile)
        val selectedDirectory by rememberUpdatedState(state.selectedFile.asDirectory)

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
                    BottomAppBar {
                        IconButton(enabled = true, onClick = { showDeleteDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.DeleteSweep,
                                contentDescription = "Delete",
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        IconButton(onClick = { showEditDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.EditNote,
                                contentDescription = "Edit",
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        IconButton(
                            enabled = state.selectedFile.isDirectory,
                            onClick = { showBookmarkDialog = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddLink,
                                contentDescription = "AddLink",
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        IconButton(
                            enabled = state.selectedFile.isDirectory,
                            onClick = { showDirectoryDialog = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.CreateNewFolder,
                                contentDescription = "AddLink",
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        IconButton(
                            enabled = state.selectedFile.isBookmark,
                            onClick = {
                                state.selectedFile.asBookmark?.let { openUrl(it.url) }
                            }
                        ) {
                            Icon(
                                painter = painterResource("browser.png"),
                                contentDescription = "Open in browser",
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                },
            ) {
                Explorer(
                    rootDirectory = state.fileTree,
                    selectedFile = state.selectedFile,
                    expandedDirs = state.expandedDirs,
                    onClickFile = { file -> screenModel.onClickFile(file) },
                    onClickArrow = { directory -> screenModel.onClickArrow(directory) },
                    onClickBookmark = { bookmark -> openUrl(bookmark.url) },
                    modifier = Modifier.fillMaxSize().padding(it)
                        .verticalScroll(rememberScrollState())
                        .clickable { screenModel.onResetFile() }
                )
            }

            if (showBookmarkDialog && selectedDirectory != null) {
                BookmarkDialog(
                    targetDirectory = selectedDirectory!!,
                    onApply = { showBookmarkDialog = false },
                    onClose = { showBookmarkDialog = false }
                )
            }

            if (showDirectoryDialog && selectedDirectory != null) {
                DirectoryDialog(
                    targetDirectory = selectedDirectory!!,
                    onApply = { showDirectoryDialog = false },
                    onClose = { showDirectoryDialog = false }
                )
            }

            if (showDeleteDialog) {
                DeleteDialog(
                    targetFile = selectedFile,
                    onApply = { showDeleteDialog = false },
                    onClose = { showDeleteDialog = false }
                )
            }

            if (showEditDialog) {
                EditDialog(
                    targetFile = selectedFile,
                    onApply = { showEditDialog = false },
                    onClose = { showEditDialog = false }
                )
            }
        }
    }
}
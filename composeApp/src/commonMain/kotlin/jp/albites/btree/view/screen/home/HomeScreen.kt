package jp.albites.btree.view.screen.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLink
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.LocalStrings
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import jp.albites.btree.util.getScreenModel
import jp.albites.btree.view.exntension.clickableNoRipple
import jp.albites.btree.view.screen.home.bookmark.BookmarkDialog
import jp.albites.btree.view.screen.home.component.HomeMenuIcon
import jp.albites.btree.view.screen.home.delete.DeleteDialog
import jp.albites.btree.view.screen.home.dicretory.DirectoryDialog
import jp.albites.btree.view.screen.home.edit.EditDialog
import jp.albites.btree.view.screen.setting.SettingScreen
import view.components.explorer.Explorer

class HomeScreen(val openUrl: (String) -> Unit) : Screen {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<HomeScreenModel>()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val strings = LocalStrings.current

        var showBookmarkDialog by remember { mutableStateOf(false) }
        var showDirectoryDialog by remember { mutableStateOf(false) }
        var showDeleteDialog by remember { mutableStateOf(false) }
        var showEditDialog by remember { mutableStateOf(false) }
        val selectedFile by rememberUpdatedState(state.selectedFile)
        val selectedDirectory by rememberUpdatedState(state.selectedFile.asDirectory)

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(strings.homeTitle) },
                    actions = {
                        IconButton(onClick = { navigator.push(SettingScreen()) }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = null,
                            )
                        }
                    },
                )
            },
            bottomBar = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Divider()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                    ) {
                        AnimatedContent(
                            targetState = state.canCreateNewFolder,
                            transitionSpec = { scaleIn() togetherWith scaleOut() },
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1.0f),
                        ) {
                            HomeMenuIcon(
                                icon = Icons.Default.CreateNewFolder,
                                label = strings.homeCreateFolder,
                                enabled = it,
                                onClick = { showDirectoryDialog = true },
                            )
                        }

                        AnimatedContent(
                            targetState = state.canAddBookmark,
                            transitionSpec = { scaleIn() togetherWith scaleOut() },
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1.0f),
                        ) {
                            HomeMenuIcon(
                                icon = Icons.Default.AddLink,
                                label = strings.homeAddBookmark,
                                enabled = it,
                                onClick = { showBookmarkDialog = true },
                            )
                        }

                        AnimatedContent(
                            targetState = state.canEdit,
                            transitionSpec = { scaleIn() togetherWith scaleOut() },
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1.0f),
                        ) {
                            HomeMenuIcon(
                                icon = Icons.Default.Edit,
                                label = strings.homeEdit,
                                enabled = it,
                                onClick = { showEditDialog = true },
                            )
                        }

                        AnimatedContent(
                            targetState = state.canDelete,
                            transitionSpec = { scaleIn() togetherWith scaleOut() },
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1.0f),
                        ) {
                            HomeMenuIcon(
                                icon = Icons.Default.Delete,
                                label = strings.homeDelete,
                                enabled = it,
                                onClick = { showDeleteDialog = true },
                            )
                        }
                    }
                }
            },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .windowInsetsPadding(WindowInsets.systemBars.union(WindowInsets.ime)),
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
                    .clickableNoRipple { screenModel.onResetFile() },
            )

            if (showBookmarkDialog && selectedDirectory != null) {
                selectedDirectory?.let { directory ->
                    BookmarkDialog(
                        targetDirectory = directory,
                        onApply = {
                            showBookmarkDialog = false
                        },
                        onClose = {
                            showBookmarkDialog = false
                        },
                    )
                }
            }

            if (showDirectoryDialog && selectedDirectory != null) {
                selectedDirectory?.let {
                    DirectoryDialog(
                        targetDirectory = it,
                        onApply = {
                            showDirectoryDialog = false

                        },
                        onClose = {
                            showDirectoryDialog = false
                        },
                    )
                }
            }

            if (showDeleteDialog) {
                DeleteDialog(
                    targetFile = selectedFile,
                    onApply = {
                        showDeleteDialog = false
                    },
                    onClose = {
                        showDeleteDialog = false
                    },
                )
            }

            if (showEditDialog) {
                EditDialog(
                    targetFile = selectedFile,
                    onApply = {
                        showEditDialog = false
                    },
                    onClose = {
                        showEditDialog = false
                    },
                )
            }
        }
    }
}

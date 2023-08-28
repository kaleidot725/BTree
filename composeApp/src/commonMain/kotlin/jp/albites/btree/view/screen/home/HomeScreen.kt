package jp.albites.btree.view.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.InsertLink
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.moriatsushi.insetsx.systemBarsPadding
import jp.albites.btree.model.domain.Directory
import jp.albites.btree.util.getScreenModel
import jp.albites.btree.view.exntension.clickableNoRipple
import jp.albites.btree.view.screen.home.bookmark.BookmarkDialog
import jp.albites.btree.view.screen.home.component.HomeMenuIcon
import jp.albites.btree.view.screen.home.delete.DeleteDialog
import jp.albites.btree.view.screen.home.dicretory.DirectoryDialog
import jp.albites.btree.view.screen.home.edit.EditDialog
import jp.albites.btree.view.screen.setting.SettingScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import view.components.explorer.Explorer

class HomeScreen(val openUrl: (String) -> Unit) : Screen {
    @OptIn(
        ExperimentalMaterial3Api::class, ExperimentalResourceApi::class,
        ExperimentalLayoutApi::class
    )
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

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
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
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(
                                color = Color.LightGray.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        HomeMenuIcon(
                            icon = Icons.Default.CreateNewFolder,
                            label = "Create Folder",
                            enabled = state.selectedFile.isDirectory,
                            onClick = { showDirectoryDialog = true },
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1.0f)
                        )

                        HomeMenuIcon(
                            icon = Icons.Default.InsertLink,
                            label = "Add Bookmark",
                            enabled = state.selectedFile.isDirectory,
                            onClick = { showBookmarkDialog = true },
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1.0f)
                        )

                        HomeMenuIcon(
                            icon = Icons.Default.Edit,
                            label = "Edit",
                            enabled = state.selectedFile.id != Directory.ROOT.id,
                            onClick = { showEditDialog = true },
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1.0f)
                        )

                        HomeMenuIcon(
                            icon = Icons.Default.Delete,
                            label = "Delete",
                            enabled = state.selectedFile.id != Directory.ROOT.id,
                            onClick = { showDeleteDialog = true },
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1.0f)
                        )
                    }
                }
            },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .systemBarsPadding()
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
                    .clickableNoRipple { screenModel.onResetFile() }
                    .systemBarsPadding()
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
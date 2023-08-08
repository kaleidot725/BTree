package jp.albites.btree.view.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AppSettingsAlt
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("BTree") },
                    actions = {
                        IconButton(onClick = { navigator.push(SettingScreen()) }) {
                            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
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
                            IconButton(onClick = { /* doSomething() */ }) {
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
                            IconButton(onClick = { /* doSomething() */ }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        AnimatedVisibility(
                            visible = selectedFile.isBookmark,
                            enter = scaleIn(),
                            exit = scaleOut()
                        ) {
                            IconButton(
                                onClick = {
                                    selectedFile.asBookmark?.let { openUrl(it.url) }
                                }
                            ) {
                                Icon(
                                    painter = painterResource("browser.png"),
                                    contentDescription = "Open in browser",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { /* do something */ },
                            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                        ) {
                            Icon(Icons.Filled.Add, "Localized description")
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
    }
}
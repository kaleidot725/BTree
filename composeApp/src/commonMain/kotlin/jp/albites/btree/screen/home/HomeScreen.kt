package jp.albites.btree.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import io.github.skeptick.libres.compose.painterResource
import jp.albites.btree.MainRes
import jp.albites.btree.component.explorer.File
import jp.albites.btree.openUrl
import view.components.explorer.Explorer

class HomeScreen(openUrl: (String) -> Unit) : Screen {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { HomeScreenModel() }
        var selectedFile: File by remember { mutableStateOf(screenModel.fileTree) }

        Scaffold(
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
                                    painter = MainRes.image.delete.painterResource(),
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
                                    painter = MainRes.image.edit.painterResource(),
                                    contentDescription = "Edit",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        AnimatedVisibility(
                            visible = !selectedFile.isDirectory,
                            enter = scaleIn(),
                            exit = scaleOut()
                        ) {
                            IconButton(onClick = { openUrl(selectedFile.url) }) {
                                Icon(
                                    painter = MainRes.image.browser.painterResource(),
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
                title = "BTree",
                targetFile = screenModel.fileTree,
                selectedFile = selectedFile,
                onClickHome = {},
                onClickFile = { selectedFile = it },
                modifier = Modifier.fillMaxSize().padding(it)
            )
        }
    }
}
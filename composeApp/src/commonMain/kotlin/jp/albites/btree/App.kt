package jp.albites.btree

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Web
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
import io.github.skeptick.libres.compose.painterResource
import jp.albites.btree.explorer.filetree.File
import jp.albites.btree.theme.AppTheme
import view.components.explorer.Explorer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun App() = AppTheme {
    val targetFile: File by remember {
        mutableStateOf(
            File(
                isDirectory = true,
                listFiles = listOf(
                    File(
                        isDirectory = true,
                        listFiles = listOf(
                            File(
                                isDirectory = false,
                                listFiles = listOf(),
                                name = "SUB DIR FILE A"
                            ),
                            File(
                                isDirectory = false,
                                listFiles = listOf(),
                                name = "SUB DIR FILE B"
                            )
                        ),
                        name = "SUB DIR"
                    ),
                    File(
                        isDirectory = false,
                        listFiles = listOf(),
                        name = "FILE A"
                    ),
                    File(
                        isDirectory = false,
                        listFiles = listOf(),
                        name = "FILE B"
                    )
                ),
                name = "ROOT"
            )
        )
    }
    var selectedFile: File by remember { mutableStateOf(targetFile) }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Default.Delete, contentDescription = "Localized description")
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            Icons.Default.EditNote,
                            contentDescription = "Localized description",
                        )
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            painter = MainRes.image.incognito.painterResource(),
                            contentDescription = "Localized description",
                        )
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            painter = MainRes.image.browser.painterResource(),
                            contentDescription = "Localized description",
                        )
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
        }
    ) {
        Explorer(
            title = "BTree",
            targetFile = targetFile,
            selectedFile = selectedFile,
            onClickHome = {},
            onClickFile = { selectedFile = it },
            modifier = Modifier.fillMaxSize()
        )
    }
}

internal expect fun openUrl(url: String?)
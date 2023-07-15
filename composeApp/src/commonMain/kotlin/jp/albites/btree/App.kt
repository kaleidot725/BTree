package jp.albites.btree

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.albites.btree.explorer.filetree.File
import jp.albites.btree.theme.AppTheme
import view.components.explorer.Explorer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun App() = AppTheme {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Check, contentDescription = "Localized description")
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            Icons.Filled.Edit,
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
            targetDirectory = File(
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
            ),
            onClickHome = {},
            onClickFile = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

internal expect fun openUrl(url: String?)
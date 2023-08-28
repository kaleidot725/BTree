package jp.albites.btree.view.screen.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import jp.albites.btree.model.domain.Theme
import jp.albites.btree.util.getScreenModel
import jp.albites.btree.view.screen.setting.component.SettingCheckItem

class ThemeScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<ThemeScreenModel>()
        val selectedTheme by screenModel.selectedTheme.collectAsState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Theme") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    },
                )
            }
        ) {
            Column(modifier = Modifier.padding(it)) {
                SettingCheckItem(
                    title = "Light",
                    icon = Icons.Default.LightMode,
                    checked = selectedTheme == Theme.LIGHT,
                    onCheckedChange = { screenModel.selectTheme(Theme.LIGHT) },
                    iconDescription = "LightMode",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(12.dp)
                )

                Divider()

                SettingCheckItem(
                    title = "Dark",
                    icon = Icons.Default.DarkMode,
                    checked = selectedTheme == Theme.DARK,
                    onCheckedChange = { screenModel.selectTheme(Theme.DARK) },
                    iconDescription = "DarkMode",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(12.dp)
                )

                Divider()

                SettingCheckItem(
                    title = "Sync System",
                    icon = Icons.Default.Sync,
                    checked = selectedTheme == Theme.SYSTEM,
                    onCheckedChange = { screenModel.selectTheme(Theme.SYSTEM) },
                    iconDescription = "Sync",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(12.dp)
                )
            }
        }
    }
}
package jp.albites.btree.view.screen.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import jp.albites.btree.view.resources.StringResource
import jp.albites.btree.view.screen.setting.component.SettingItem
import jp.albites.btree.view.screen.theme.ThemeScreen

class SettingScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(StringResource.settingTitle()) },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    },
                )
            },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .windowInsetsPadding(WindowInsets.systemBars),
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(it)) {
                SettingItem(
                    title = StringResource.settingThemeTitle(),
                    icon = Icons.Default.ColorLens,
                    iconDescription = "theme",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .clickable(onClick = { navigator.push(ThemeScreen()) })
                        .padding(12.dp),
                )

                Divider()

                SettingItem(
                    title = StringResource.settingVersion(),
                    icon = Icons.Default.CardMembership,
                    iconDescription = "Version",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .clickable(onClick = {})
                        .padding(12.dp),
                )
            }
        }
    }
}

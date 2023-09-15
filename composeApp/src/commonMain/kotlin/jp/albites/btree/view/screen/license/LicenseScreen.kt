package jp.albites.btree.view.screen.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.lyricist.LocalStrings
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import jp.albites.btree.util.getScreenModel
import jp.albites.btree.view.screen.license.component.LicenseCard

class LicenseScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<LicenseScreenModel>()
        val state by screenModel.state.collectAsState()
        val strings = LocalStrings.current

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(strings.settingLicenseTitle) },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    },
                )
            },
            content = {
                if (state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(it)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(it)
                    ){
                        state.licenses.forEach { license ->
                            item { LicenseCard(license) }
                        }
                    }
                }
            },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .windowInsetsPadding(WindowInsets.systemBars),
        )
    }
}

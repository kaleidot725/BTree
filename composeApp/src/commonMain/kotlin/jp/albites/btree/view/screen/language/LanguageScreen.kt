package jp.albites.btree.view.screen.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
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
import cafe.adriel.lyricist.LocalStrings
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import jp.albites.btree.model.domain.Language
import jp.albites.btree.util.getScreenModel
import jp.albites.btree.view.screen.setting.component.SettingCheckItem

class LanguageScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<LanguageScreenModel>()
        val selectedLanguage by screenModel.selectedLanguage.collectAsState()
        val strings = LocalStrings.current

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(strings.settingLanguageTitle) },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    },
                )
            },
        ) {
            Column(modifier = Modifier.padding(it)) {
                SettingCheckItem(
                    title = strings.settingLanguageEnglish,
                    icon = Icons.Default.Language,
                    checked = selectedLanguage == Language.ENGLISH,
                    onCheckedChange = { screenModel.selectLanguage(Language.ENGLISH) },
                    iconDescription = "ENGLISH",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(12.dp),
                )

                Divider()

                SettingCheckItem(
                    title = strings.settingLanguageJapanese,
                    icon = Icons.Default.Language,
                    checked = selectedLanguage == Language.JAPANESE,
                    onCheckedChange = { screenModel.selectLanguage(Language.JAPANESE) },
                    iconDescription = "JAPANESE",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(12.dp),
                )
            }
        }
    }
}

package jp.albites.btree.view.screen.license.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import jp.albites.btree.model.domain.license.License
import jp.albites.btree.util.openUrl

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LicenseCard(
    license: License
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = license.name, style = MaterialTheme.typography.titleMedium)
            Text(text = license.artifactId, style = MaterialTheme.typography.titleSmall)
            Text(text = license.version, style = MaterialTheme.typography.titleSmall)
            FlowRow {
                license.spdxLicenses.forEach { spdxLicense ->
                    ClickableText(
                        buildAnnotatedString {
                            append(spdxLicense.name)
                            addStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    textDecoration = TextDecoration.Underline
                                ),
                                start = 0,
                                end = spdxLicense.name.length
                            )
                            addStringAnnotation(
                                tag = spdxLicense.name,
                                annotation = spdxLicense.url,
                                start = 0,
                                end = spdxLicense.name.length
                            )
                        },
                        onClick = {
                            openUrl(spdxLicense.url)
                        },
                    )
                }
            }
        }
    }
}
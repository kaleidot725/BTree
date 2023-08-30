package jp.albites.btree.util

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import org.koin.compose.koinInject
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

@Composable
inline fun <reified T : ScreenModel> Screen.getScreenModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
): T {
    val value = koinInject<T>(qualifier = qualifier, parameters = parameters)
    return rememberScreenModel(tag = qualifier?.value) { value }
}

@Composable
inline fun <reified T : ScreenModel> Screen.getDialogModel(
    tag: String,
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
): T {
    val value = koinInject<T>(qualifier = qualifier, parameters = parameters)
    return rememberScreenModel(tag = tag) { value }
}

package jp.albites.btree.view.screen.theme

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import jp.albites.btree.model.domain.license.License
import jp.albites.btree.model.repository.LicenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LicenseScreenModel(
    private val licenseRepository: LicenseRepository,
) : ScreenModel {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    init {
        coroutineScope.launch {
            val licenses = licenseRepository.get()
            _state.value = State(isLoading = false, licenses = licenses)
        }
    }

    data class State(
        val isLoading: Boolean = true,
        val licenses: List<License> = emptyList(),
    )
}

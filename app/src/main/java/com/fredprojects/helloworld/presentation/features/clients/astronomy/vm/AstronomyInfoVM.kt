package com.fredprojects.helloworld.presentation.features.clients.astronomy.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredprojects.helloworld.domain.core.utils.ConnectionStatus
import com.fredprojects.helloworld.domain.features.clients.astronomy.models.AstronomyInfo
import com.fredprojects.helloworld.domain.features.clients.astronomy.repository.IAstronomyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AstronomyInfoVM(
    private val repository: IAstronomyRepository
) : ViewModel() {
    private val astronomyInfoMSF = MutableStateFlow<ConnectionStatus<AstronomyInfo>>(ConnectionStatus.Loading(emptyList()))
    val astronomyInfo = astronomyInfoMSF.asStateFlow()
    fun getAstronomyInfo(ra: String, dec: String, radius: Float) {
        viewModelScope.launch {
            repository.getData(ra, dec, radius).flowOn(Dispatchers.IO).collectLatest {
                astronomyInfoMSF.emit(it)
            }
        }
    }
}
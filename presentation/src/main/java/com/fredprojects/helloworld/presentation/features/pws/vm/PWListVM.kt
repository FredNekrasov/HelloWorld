package com.fredprojects.helloworld.presentation.features.pws.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredprojects.helloworld.domain.core.utils.SortType
import com.fredprojects.helloworld.domain.features.pws.models.PracticalWork
import com.fredprojects.helloworld.domain.features.pws.useCases.PWUseCases
import com.fredprojects.helloworld.domain.features.pws.utils.SortingPW
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * PWListVM is used to manage the state of the list of practical works
 */
class PWListVM(
    private val useCases: PWUseCases
) : ViewModel()  {
    /**
     * @see pwStateMSF is used to emit data to the state flow
     * @see pwState is used to display data in the view
     */
    private val pwStateMSF = MutableStateFlow(PWState())
    val pwState = pwStateMSF.asStateFlow()
    private var recentlyDeletedPW: PracticalWork? = null
    private var getDataJob: Job? = null
    /**
     * The onEvent is used to manage data or view events
     */
    fun onEvent(event: PWEvents) {
        when(event) {
            is PWEvents.DeletePW -> deletePW(event.pw)
            PWEvents.RestorePW -> restorePW()
            is PWEvents.SearchPW -> findPWsByValue(event.value)
            is PWEvents.Sort -> getSortedPWs(event.sortingPW)
            PWEvents.ToggleSortSection -> pwStateMSF.value = pwState.value.copy(isSortingSectionVisible = !pwState.value.isSortingSectionVisible)
        }
    }
    /**
     * The getSortedPWs is used to get the sorted list of practical works
     * @param sortingPW is the sorting type of the practical works
     */
    private fun getSortedPWs(sortingPW: SortingPW) {
        if ((pwState.value.sortingPW::class == sortingPW::class) && (pwState.value.sortingPW.sortType == sortingPW.sortType)) return
        getDataJob?.cancel()
        getDataJob = useCases.getPWs(sortingPW).onEach {
            pwStateMSF.emit(pwState.value.copy(pws = it, sortingPW = sortingPW))
        }.launchIn(viewModelScope)
    }
    /**
     * The findPWsByValue is used to get the sorted list of practical works and search by value in the list
     * @param value is the value to be searched
     */
    private fun findPWsByValue(value: String) {
        getDataJob?.cancel()
        getDataJob = useCases.getPWs.find(value, pwState.value.sortingPW).onEach {
            pwStateMSF.emit(pwState.value.copy(pws = it))
        }.launchIn(viewModelScope)
    }
    /**
     * The deletePW is used to delete a practical work from the database
     * @param pw is the practical work to be deleted
     */
    private fun deletePW(pw: PracticalWork) {
        viewModelScope.launch {
            useCases.delete(pw)
            recentlyDeletedPW = pw
        }
    }
    /**
     * The restorePW is used to restore a deleted practical work
     */
    private fun restorePW() {
        viewModelScope.launch {
            recentlyDeletedPW?.let { pw ->
                useCases.upsert(pw)
                recentlyDeletedPW = null
            }
        }
    }
    /**
     * The init is used to get the sorted list of practical works by date on creation of the class
     */
    init { getSortedPWs(SortingPW.Date(SortType.Descending)) }
}
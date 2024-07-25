package com.fredprojects.helloworld.presentation.features.inequality.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredprojects.helloworld.R
import com.fredprojects.helloworld.domain.features.inequality.useCases.InequalityUseCase
import com.fredprojects.helloworld.domain.features.inequality.utils.InequalityStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InequalityVM(
    private val inequalityUseCase: InequalityUseCase
) : ViewModel() {
    private val solutionMSF = MutableStateFlow<Any>("")
    val solution = solutionMSF.asStateFlow()
    fun solveTheInequality(a: Float?, b: Float?) {
        viewModelScope.launch {
            val result = when(inequalityUseCase(a, b)) {
                InequalityStatus.ERROR -> R.string.error
                InequalityStatus.NO_SOLUTION -> R.string.inequalityHasNoSolutions
                InequalityStatus.FIRST_SOLUTION -> "$b < 0\nx = (− ∞ , + ∞)"
                InequalityStatus.SECOND_SOLUTION -> "x > ${-b!!/a!!}\nx = (${-b/a}, + ∞)"
                InequalityStatus.THIRD_SOLUTION -> "x < ${-b!!/a!!}\nx = (− ∞ , ${-b/a})"
            }
            solutionMSF.emit(result)
        }
    }
}
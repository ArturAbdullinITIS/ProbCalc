package com.example.probcalc.presentation.screens.prob

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.example.probcalc.domain.usecase.CalculateCombUseCase
import com.example.probcalc.domain.usecase.CalculateProbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProbScreenViewModel @Inject constructor(
    private val calculateProbUseCase: CalculateProbUseCase
): ViewModel() {
    private val _state = MutableStateFlow(ProbScreenState())
    val state = _state.asStateFlow()

    fun processCommand(command: ProbScreenCommands) {
        when(command) {
            ProbScreenCommands.Calculate -> {
                _state.update { state ->
                    state.copy(
                        result = calculateProbUseCase(state)
                    )
                }
            }
            is ProbScreenCommands.ChangeAllMarked -> {
                _state.update { state ->
                    state.copy(
                        allMarked = command.allMarked
                    )
                }
            }
            is ProbScreenCommands.InputK -> {
                _state.update { state ->
                    state.copy(
                        k = command.k
                    )
                }
            }
            is ProbScreenCommands.InputM -> {
                _state.update { state ->
                    state.copy(
                        m = command.m
                    )
                }
            }
            is ProbScreenCommands.InputN -> {
                _state.update { state ->
                    state.copy(
                        n = command.n
                    )
                }
            }
            is ProbScreenCommands.InputR -> {
                _state.update { state ->
                    state.copy(
                        r = command.r
                    )
                }
            }
        }
    }
}


sealed interface ProbScreenCommands{
    data class InputN(val n: String): ProbScreenCommands
    data class InputK(val k: String): ProbScreenCommands
    data class InputM(val m: String): ProbScreenCommands
    data class InputR(val r: String): ProbScreenCommands
    data class ChangeAllMarked(val allMarked: Boolean): ProbScreenCommands
    data object Calculate: ProbScreenCommands
}
data class ProbScreenState(
    val n: String = "",
    val k: String = "",
    val m: String = "",
    val r: String = "",
    val allMarked: Boolean = false,
    val result: String = "0",
    val resultError: String = ""
)
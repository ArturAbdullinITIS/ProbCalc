package com.example.probcalc.presentation.screens.comb


import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.example.probcalc.domain.model.CalcType
import com.example.probcalc.domain.usecase.CalculateCombUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class CombScreenViewModel @Inject constructor(
    private val calculateCombUseCase: CalculateCombUseCase
): ViewModel() {
    private val _state = MutableStateFlow(CombScreenState())

    val state = _state.asStateFlow()

    fun processCommand(command: CombScreenCommands) {
        when(command) {
            is CombScreenCommands.InputK -> {
                _state.update { state ->
                    state.copy(
                        k = command.k
                    )
                }
            }
            is CombScreenCommands.InputN -> {
                _state.update { state ->
                    state.copy(
                        n = command.n
                    )
                }
            }
            CombScreenCommands.Calculate -> {

                _state.update { state ->
                    state.copy(
                        result = calculateCombUseCase(
                            state
                        )
                    )
                }
            }
            is CombScreenCommands.ChangeType -> {
                _state.update { state ->
                    state.copy(
                        type = command.type
                    )
                }
            }

            is CombScreenCommands.ChangeWithRepeats -> {
                _state.update { state ->
                    state.copy(
                        withRepeats = command.withRepeats
                    )
                }
            }
        }
    }
}



sealed interface CombScreenCommands {
    data class InputN(val n: String): CombScreenCommands
    data class InputK(val k: String): CombScreenCommands
    data class ChangeType(val type: CalcType): CombScreenCommands
    data class ChangeWithRepeats(val withRepeats: Boolean): CombScreenCommands
    data object Calculate: CombScreenCommands

}


data class CombScreenState(
    val n: String = "",
    val k: String = "",
    val type: CalcType = CalcType.PLACEMENT,
    val withRepeats: Boolean = false,
    val result: String = "0",
    val resultError: String = ""
)
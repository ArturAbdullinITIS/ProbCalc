package com.example.probcalc.presentation.screens.comb

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.probcalc.domain.model.CalcType
import ru.itis.notifications.R


// CombScreen.kt
@Composable
fun CombScreen() {
    CombScreenContent()
}

@Composable
private fun CombScreenContent(
    modifier: Modifier = Modifier
) {
    val viewModel: CombScreenViewModel = hiltViewModel()
    val currentState by viewModel.state.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    val isErrorResult = currentState.result.startsWith("Error") ||
            currentState.result.startsWith("Please enter") ||
            currentState.result.startsWith("Для перестановок") ||
            currentState.result.startsWith("Ошибка") ||
            currentState.result.contains("cannot") ||
            currentState.result.contains("must be") ||
            currentState.result.contains("должна") ||
            currentState.result.contains("должны") ||
            currentState.result.contains("неверный формат") ||
            currentState.result == "Calculation error" ||
            currentState.result == "Ошибка вычисления"

    // Определяем, нужно ли показывать поле для counts
    val showCountsField = currentState.type == CalcType.PERMUTATION && currentState.withRepeats
    // Определяем, нужно ли показывать чекбокс WithRepeats
    Scaffold(
        modifier = Modifier
            .imePadding()
            .navigationBarsPadding()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Title(
                    modifier = modifier,
                    value = stringResource(R.string.combinatorics_screen)
                )

                Spacer(modifier = Modifier.height(8.dp))

                CustomDropDownPanel(
                    modifier = modifier,
                    selected = currentState.type,
                    onSelect = {
                        viewModel.processCommand(CombScreenCommands.ChangeType(it))
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                )

                Spacer(modifier = Modifier.height(8.dp))

                InputNTextField(
                    modifier = modifier,
                    value = currentState.n,
                    onValueChange = {
                        viewModel.processCommand(CombScreenCommands.InputN(it))
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (currentState.type != CalcType.PERMUTATION) {
                    InputKTextField(
                        modifier = modifier,
                        value = currentState.k,
                        onValueChange = {
                            viewModel.processCommand(CombScreenCommands.InputK(it))
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (showCountsField) {
                    InputCountsTextField(
                        modifier = modifier,
                        value = currentState.counts,
                        onValueChange = {
                            viewModel.processCommand(CombScreenCommands.InputCounts(it))
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                WithRepeats(
                    modifier = modifier,
                    onCheckedChange = {
                        viewModel.processCommand(CombScreenCommands.ChangeWithRepeats(it))
                    },
                    checked = currentState.withRepeats
                )
                Spacer(modifier = Modifier.height(8.dp))

                ResultTitle(
                    value = stringResource(R.string.result_comb)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Result(
                    value = currentState.result,
                    isError = isErrorResult
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                CalculateButton(
                    modifier = modifier,
                    onClick = {
                        viewModel.processCommand(CombScreenCommands.Calculate)
                    }
                )
            }
        }
    }
}

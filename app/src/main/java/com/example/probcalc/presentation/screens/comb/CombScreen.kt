package com.example.probcalc.presentation.screens.comb

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel


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
            currentState.result.contains("cannot") ||
            currentState.result.contains("must be") ||
            currentState.result == "Calculation error"

    Column(
        modifier = Modifier
            .padding(16.dp)
            .imePadding()
            .navigationBarsPadding()
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Title(
                modifier = modifier,
                value = "Combinatorics"
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
            InputKTextField(
                modifier = modifier,
                value = currentState.k,
                onValueChange = {
                    viewModel.processCommand(CombScreenCommands.InputK(it))
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            WithRepeats(
                modifier = modifier,
                onCheckedChange = {
                    viewModel.processCommand(CombScreenCommands.ChangeWithRepeats(it))
                },
                checked = currentState.withRepeats
            )
            Spacer(modifier = Modifier.height(8.dp))

            ResultTitle(
                value = "Result"
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

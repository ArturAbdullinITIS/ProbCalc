package com.example.probcalc.presentation.screens.prob

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun ProbScreen() {
    ProbScreenContent()
}


@Composable
private fun ProbScreenContent(
    modifier: Modifier = Modifier
) {
    val viewModel: ProbScreenViewModel = hiltViewModel()
    val currentState by viewModel.state.collectAsState()
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
                value = "Probability"
            )
            Spacer(modifier = Modifier.height(8.dp))
            InputNTextField(
                modifier = modifier,
                value = currentState.n,
                onValueChange = {
                    viewModel.processCommand(ProbScreenCommands.InputN(it))
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            InputKTextField(
                modifier = modifier,
                value = currentState.k,
                onValueChange = {
                    viewModel.processCommand(ProbScreenCommands.InputK(it))
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            InputMTextField(
                modifier = modifier,
                value = currentState.m,
                onValueChange = {
                    viewModel.processCommand(ProbScreenCommands.InputM(it))
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            if(currentState.allMarked) {
                InputRTextField(
                    modifier = modifier,
                    value = currentState.r,
                    onValueChange = {
                        viewModel.processCommand(ProbScreenCommands.InputR(it))
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            AllMarked(
                modifier = modifier,
                onCheckedChange = {
                    viewModel.processCommand(ProbScreenCommands.ChangeAllMarked(it))
                },
                checked = currentState.allMarked
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
                    viewModel.processCommand(ProbScreenCommands.Calculate)
                }
            )
        }

    }

}
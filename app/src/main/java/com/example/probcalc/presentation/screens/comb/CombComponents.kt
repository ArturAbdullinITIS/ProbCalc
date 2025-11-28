package com.example.probcalc.presentation.screens.comb

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.probcalc.domain.model.CalcType

@Composable
fun InputNTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = { newValue ->
            // Allow only digits
            if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                onValueChange(newValue)
            }
        },
        placeholder = { Text("Input n value") },
        singleLine = true,
        shape = RoundedCornerShape(20.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun InputKTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = { newValue ->
            // Allow only digits
            if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                onValueChange(newValue)
            }
        },
        placeholder = { Text("Input k value") },
        singleLine = true,
        shape = RoundedCornerShape(20.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun CalculateButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        enabled = enabled
    ) {
        Text(text = "Calculate")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropDownPanel(
    modifier: Modifier = Modifier,
    selected: CalcType,
    onSelect: (CalcType) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .menuAnchor(),
            value = getCalcTypeDisplayName(selected),
            onValueChange = { },
            readOnly = true,
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            placeholder = {
                Text("Select Type")
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            CalcType.entries.forEach { type ->
                DropdownMenuItem(
                    text = {
                        Text(getCalcTypeDisplayName(type))
                    },
                    onClick = {
                        onSelect(type)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}

@Composable
private fun getCalcTypeDisplayName(type: CalcType): String {
    return when (type) {
        CalcType.PLACEMENT -> "Placement"
        CalcType.PERMUTATION -> "Permutation"
        CalcType.COMBINATION -> "Combination"
    }
}

@Composable
fun WithRepeats(
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit,
    checked: Boolean
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = "With repeats"
        )
    }
}

@Composable
fun Title(
    modifier: Modifier = Modifier,
    value: String
) {
    Text(
        modifier = modifier
            .fillMaxWidth(),
        text = value,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun ResultTitle(
    modifier: Modifier = Modifier,
    value: String
) {
    Text(
        modifier = modifier
            .fillMaxWidth(),
        text = value,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Result(
    modifier: Modifier = Modifier,
    value: String,
    isError: Boolean = false
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = { },
        singleLine = true,
        readOnly = true,
        shape = RoundedCornerShape(20.dp),
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    text = "Error",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}
package com.example.planrecords.screen
// AddPlanScreen.kt
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
// import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
// import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.planrecords.viewmodel.AddPlanViewModel

@Composable
fun AddPlanScreen(viewModel: AddPlanViewModel = hiltViewModel(),onCancel: (String) -> Unit, navController: NavHostController) {
    val planName = remember { mutableStateOf(TextFieldValue()) }
    val targetCount = remember { mutableStateOf(TextFieldValue()) }
    val frequency = rememberSaveable { mutableStateOf("Daily") }
// 用于滚动的状态
   // val scrollState = rememberScrollState()
    Scaffold(
        // Scaffold 支持 FloatingActionButton 和可滚动内容
        floatingActionButton = {
            FloatingActionButton(onClick = { /* 保存逻辑 */ }) {
                Icon(Icons.Default.Create, contentDescription = "Save Plan")
            }
        }
    ){ paddingValues ->
        LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
            item {
                Text(text = "Add New Plan", style = MaterialTheme.typography.bodyMedium)
            }
            item {
        TextField(
            value = planName.value,
            onValueChange = { planName.value = it },
            label = { Text("Plan Name") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )}
            item {
        TextField(
            value = targetCount.value,
            onValueChange = { targetCount.value = it },
            label = { Text("Target Count") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )}
            item {

            FrequencySelection(frequency = frequency)
            Text("Selected Frequency: ${frequency.value}", modifier = Modifier.padding(top = 16.dp))
        }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            // 保存計畫到資料庫
                            val name = planName.value.text
                            val target = targetCount.value.text.toIntOrNull() ?: 0
                            val freq = frequency.value

                            if (name.isNotEmpty() && target > 0) {
                                viewModel.addPlan(name, target, freq)
                                navController.navigateUp()
                            }
                        }
                    ) {
                        Text("Save")
                    }

                    Button(
                        onClick = { onCancel("someInfo") },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                    ) {
                        Text("Cancel")
                    }
                }
            }
    }
}
}


@Composable
fun FrequencySelection(frequency: MutableState<String>) {
    val options = listOf("Daily", "Weekly", "Monthly")

    Column {
        Text("Select Frequency:")

        // 將每個選項顯示為單選按鈕
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                RadioButton(
                    selected = (option == frequency.value),
                    onClick = { frequency.value = option }
                )
                Text(text = option, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

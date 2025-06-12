package com.example.cprtrainingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class ModelDevice(val id: String, val name: String, val isConnected: Boolean)

@Composable
fun HomeScreen(navController: NavController) {
    val models = listOf(
        ModelDevice("CPR-01", "Cardiopulmonary Resuscitation", false),
        ModelDevice("CPR-02", "Model 2", false),
        ModelDevice("CPR-03", "Model 3", false),
        ModelDevice("CPR-04", "Model 4", false),
        ModelDevice("CPR-05", "Model 5", false),
        ModelDevice("CPR-06", "Model 6", false),
    )

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Select Model", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(models) { model ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (model.isConnected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(model.name)
                            Text(if (model.isConnected) "Connected" else "Disconnected")
                        }
                    }
                }
            }
            Button(
                onClick = {
                    navController.navigate("training")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Start Training")
            }
        }
    }
}

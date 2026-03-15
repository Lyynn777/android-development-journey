package com.example.rsvpapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RsvpScreen()
                }
            }
        }
    }
}

@Composable
fun RsvpScreen() {

    // State Variables
    var guestsInput by remember { mutableStateOf("") }
    var mealPreferenceInput by remember { mutableStateOf<String?>(null) }
    var rsvpStatus by remember { mutableStateOf(false) }
    var resultMessage by remember { mutableStateOf("") }

    // Convert String to Int safely
    val guests: Int? = guestsInput.toIntOrNull()

    // Default meal preference if null
    val mealPreference = mealPreferenceInput ?: "Standard"

    // Submit button enabled condition
    val isSubmitEnabled = rsvpStatus && (guests != null && guests > 0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(text = "Event RSVP")

        // Guests Input
        OutlinedTextField(
            value = guestsInput,
            onValueChange = { guestsInput = it },
            label = { Text("Number of Guests") }
        )

        // Simple Meal Selection Buttons
        Text(text = "Meal Preference")

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            Button(onClick = { mealPreferenceInput = "Standard" }) {
                Text("Standard")
            }

            Button(onClick = { mealPreferenceInput = "Vegetarian" }) {
                Text("Vegetarian")
            }

            Button(onClick = { mealPreferenceInput = "Vegan" }) {
                Text("Vegan")
            }
        }

        Text(text = "Selected: $mealPreference")

        // RSVP Toggle
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "Accept Invitation")
            Switch(
                checked = rsvpStatus,
                onCheckedChange = { rsvpStatus = it }
            )
        }

        // Submit Button
        Button(
            onClick = {
                if (!rsvpStatus) {
                    resultMessage = "Thank you for your response."
                } else if (guests != null && guests > 0) {
                    resultMessage =
                        "RSVP Confirmed for $guests guests. Meal: $mealPreference"
                } else {
                    resultMessage = "Please enter valid guest count."
                }
            },
            enabled = isSubmitEnabled
        ) {
            Text("Submit")
        }

        // Result Message
        Text(text = resultMessage)
    }
}
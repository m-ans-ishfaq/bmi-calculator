package com.example.test1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test1.ui.theme.Test1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Test1Theme {
                BMIScreen()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BMIScreen()
                }
            }
        }
    }
}

// Darker Tailwind colors for light theme readability
val Cyan700 = Color(0xFF0E7490)   // Underweight
val Emerald700 = Color(0xFF047857) // Normal
val Amber700 = Color(0xFFB45309)   // Overweight
val Rose700 = Color(0xFFBE123C)    // Obese

@Composable
fun BMIScreen() {
    var age by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    var bmiResult by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var categoryColor by remember { mutableStateOf(Color.Black) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Centers everything vertically
    ) {
        Text(text = "BMI Calculator", fontSize = 28.sp)

        Spacer(modifier = Modifier.height(24.dp))

        // Changed to OutlinedTextField to remove the weird gray background
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Height (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            val h = height.toDoubleOrNull()
            val w = weight.toDoubleOrNull()

            if (h != null && w != null && h > 0) {
                val heightInMeters = h / 100
                val bmi = w / (heightInMeters * heightInMeters)

                bmiResult = String.format("%.1f", bmi)

                if (bmi < 18.5) {
                    category = "Underweight"
                    categoryColor = Cyan700
                } else if (bmi < 25.0) {
                    category = "Normal Weight"
                    categoryColor = Emerald700
                } else if (bmi < 30.0) {
                    category = "Overweight"
                    categoryColor = Amber700
                } else {
                    category = "Obese"
                    categoryColor = Rose700
                }
            }
        }) {
            Text("Calculate BMI")
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (bmiResult.isNotEmpty()) {
            Text(text = "Your BMI is: $bmiResult", fontSize = 24.sp)
            Text(text = category, fontSize = 22.sp, color = categoryColor)
        }
    }
}
package com.plcoding.backgroundlocationtracking.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.plcoding.backgroundlocationtracking.R
import com.plcoding.backgroundlocationtracking.navigation.Screen

@Composable
fun SignInScreen(
    navController: NavController
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface) //TODO Color is not showing black
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        val passwordVisible = remember {
            mutableStateOf(false)
        }
        val context = LocalContext.current

        // User ID TextField
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(
                        imageVector = if (passwordVisible.value)
                            ImageVector.vectorResource(R.drawable.baseline_visibility_24)
                        else ImageVector.vectorResource(R.drawable.baseline_visibility_off_24),
                        contentDescription = "Password visibility",
                        tint = if (passwordVisible.value) colorResource(id = R.color.purple_700) else Color.Gray
                    )
                }
            },
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),

            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom =10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                errorLabelColor = MaterialTheme.colorScheme.error,

                )
        )

        // Sign In Button
        Button(
            onClick = {
                //TODO Sign In
                navController.navigate(Screen.HomeScreen.route)

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary)
        ) {
            Text(text = "Sign In", color = MaterialTheme.colorScheme.primary)
        }

        // Sign Up Text
        TextButton(onClick = {
            navController.navigate(Screen.SignUpScreen.route)
        }) {
            Text(
                text = "Don't have an account? Sign Up.",
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview(){
    SignInScreen(navController = NavController(LocalContext.current))
}
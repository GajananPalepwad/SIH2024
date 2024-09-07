package com.plcoding.backgroundlocationtracking.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import com.plcoding.backgroundlocationtracking.components.AppTextField
import com.plcoding.backgroundlocationtracking.components.DropDownMenu
import com.plcoding.backgroundlocationtracking.navigation.Screen

@Composable
fun SignUpScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        var mobilenumber by remember {
            mutableStateOf("")
        }
        val passwordVisible = remember {
            mutableStateOf(false)
        }

        var name by remember {
            mutableStateOf("")
        }
        var employeeId by remember {
            mutableStateOf("")
        }
        var orgName by remember {
            mutableStateOf("")
        }
        var workingPosition by remember {
            mutableStateOf("")
        }



        val organizations = listOf("Organization 1", "Organization 2", "Organization 3")
        val positions = listOf("Position 1", "Position 2", "Position 3")


        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Sign Up",
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = MaterialTheme.typography.headlineMedium.fontWeight,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 50.dp, start = 20.dp, top = 20.dp),
            )
        }

        Card(
            shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Bottom,
            ) {

                AppTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Name",
                    keyboardType = KeyboardType.Text
                )

                AppTextField(
                    value = employeeId,
                    onValueChange = { employeeId = it },
                    label = "Employee Id",
                    keyboardType = KeyboardType.Text
                )
                AppTextField(
                    value = mobilenumber,
                    onValueChange = { mobilenumber = it },
                    label = " Mobile Number",
                    keyboardType = KeyboardType.Number
                )

                DropDownMenu(
                    selectedValue = orgName,
                    options = organizations,
                    label = "Select Organization",
                    onValueChangedEvent = { orgName = it },
                )
                DropDownMenu(
                    selectedValue = workingPosition,
                    options = positions,
                    label = "Select You Position",
                    onValueChangedEvent = { workingPosition = it },
                )

                AppTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    keyboardType = KeyboardType.Email)

                //Special Text field for password
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it.trim() },
                    label = { Text("Password") },
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedLabelColor = MaterialTheme.colorScheme.secondary,
                        focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                        focusedTextColor = MaterialTheme.colorScheme.secondary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        errorLabelColor = MaterialTheme.colorScheme.error,

                        )
                )

                // Sign Up Button
                Button(
                    onClick = {

                        //TODO Save mobile number to SharedPreferences
                        navController.navigate(Screen.MainScaffoldScreen.route)


                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Create Account", color = MaterialTheme.colorScheme.onPrimary)
                }

                // Sign Up Text
                TextButton(onClick = {
                    navController.navigateUp()
                }) {
                    Text(
                        text = "Already have an account? Sign In.",
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(navController = NavController(LocalContext.current))
}

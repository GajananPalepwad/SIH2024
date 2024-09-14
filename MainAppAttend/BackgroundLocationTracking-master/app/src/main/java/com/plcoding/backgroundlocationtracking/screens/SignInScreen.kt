package com.plcoding.backgroundlocationtracking.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.plcoding.backgroundlocationtracking.R
import com.plcoding.backgroundlocationtracking.api.ApiService
import com.plcoding.backgroundlocationtracking.api.RetrofitClient
import com.plcoding.backgroundlocationtracking.apimodels.request.EmployeeLogin
import com.plcoding.backgroundlocationtracking.apimodels.response.EmployeeSignInResponse
import com.plcoding.backgroundlocationtracking.data.PreferenceHelper
import com.plcoding.backgroundlocationtracking.navigation.Screen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SignInScreen(
    navController: NavController
) {
    // Sign In Screen
    val context = LocalContext.current
    val preferenceHelper = PreferenceHelper(context)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
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

        val BASE_URL = context.getString(R.string.base_url)
        val retrofit = RetrofitClient.getClient(BASE_URL)
        val apiService = retrofit?.create(ApiService::class.java)

        // User ID TextField

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Sign In",
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

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    shape = MaterialTheme.shapes.medium,
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
                    shape = MaterialTheme.shapes.medium,
                    label = { Text("Password") },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                            Icon(
                                imageVector = if (passwordVisible.value)
                                    ImageVector.vectorResource(R.drawable.baseline_visibility_24)
                                else ImageVector.vectorResource(R.drawable.baseline_visibility_off_24),
                                contentDescription = "Password visibility",
                                tint = if (passwordVisible.value) MaterialTheme.colorScheme.primary else Color.Gray
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
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
                        if (email.isEmpty() || password.isEmpty()) {
                            Toast.makeText(
                                context,
                                "Please fill all the fields",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        } else {
                            signInEmployee(
                                employee = EmployeeLogin(
                                    email, password
                                ), navController = navController,
                                context = context,
                                apiService = apiService,
                                preferenceHelper = preferenceHelper
                            )
                        }


                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp, top = 10.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Sign In", color = MaterialTheme.colorScheme.surface)
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
    }
}

private fun signInEmployee(
    employee: EmployeeLogin,
    apiService: ApiService?,
    context: Context,
    navController: NavController,
    preferenceHelper: PreferenceHelper
) {
    apiService?.employeeLogin(employee)?.enqueue(object : Callback<EmployeeSignInResponse> {
        override fun onResponse(call: Call<EmployeeSignInResponse>, response: Response<EmployeeSignInResponse>) {
            if (response.isSuccessful) {
                val userResponse = response.body()

                if (userResponse?.status == "success") {

                    // Store user data in preferences
                    preferenceHelper.userId = userResponse.user.user_id
                    preferenceHelper.name = userResponse.user.name
                    preferenceHelper.email = userResponse.user.email
                    preferenceHelper.mobileNumber = userResponse.user.mobile_number
                    preferenceHelper.orgId = userResponse.user.org_id
                    preferenceHelper.officeId = userResponse.user.office_id
                    preferenceHelper.position = userResponse.user.position
                    preferenceHelper.approved = userResponse.user.approved
                    preferenceHelper.employeeId = userResponse.user.employeeId

                    navController.navigate(Screen.MainScaffoldScreen.route)
                } else if (userResponse?.status == "error") {
                    Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                }

            } else {
                Log.d("SignIn", "Login failed: ${response.message()}")
                Toast.makeText(context, "Login failed: ${response.message()}", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<EmployeeSignInResponse>, t: Throwable) {
            Log.d("SignIn", "Network Error: ${t.message}")
            Toast.makeText(context, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
        }
    })
}


@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen(navController = NavController(LocalContext.current))
}
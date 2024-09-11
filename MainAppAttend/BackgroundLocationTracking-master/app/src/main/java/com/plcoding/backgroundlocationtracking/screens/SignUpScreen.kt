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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.plcoding.backgroundlocationtracking.api.ApiService
import com.plcoding.backgroundlocationtracking.api.RetrofitClient
import com.plcoding.backgroundlocationtracking.components.AppTextField
import com.plcoding.backgroundlocationtracking.components.DropDownMenu
import com.plcoding.backgroundlocationtracking.models.request.EmployeeRegistration
import com.plcoding.backgroundlocationtracking.models.response.ApiResponse
import com.plcoding.backgroundlocationtracking.models.response.EmployeePositionListResponse
import com.plcoding.backgroundlocationtracking.models.response.OfficeListResponse
import com.plcoding.backgroundlocationtracking.models.response.OrganisationData
import com.plcoding.backgroundlocationtracking.models.response.OrganisationListResponse
import com.plcoding.backgroundlocationtracking.navigation.Screen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var organisationListResponse = mutableListOf<OrganisationData>()

@Composable
fun SignUpScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val BASE_URL = "https://sggsapp.co.in/sih/"//context.getString(R.string.base_url)
    val retrofit = RetrofitClient.getClient(BASE_URL)
    val apiService = retrofit?.create(ApiService::class.java)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState),
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
        var selectedOrgIndex by remember { mutableIntStateOf(-1) }


        var offName by remember {
            mutableStateOf("")
        }
        var selectedOfficeIndex by remember { mutableIntStateOf(-1) }

        var workingPosition by remember {
            mutableStateOf("")
        }
        var selectedPositionIndex by remember { mutableIntStateOf(-1) }


        val organizations = mutableListOf<String>()
        val offices = mutableListOf<String>()
        val positions = mutableListOf<String>()

        getOrganisationList(organizations, apiService, context)



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
                    onValueChangedEvent = { selectedValue, Index ->
                        orgName = selectedValue
                        selectedOrgIndex = Index
                        getOfficeList(
                            offices,
                            apiService,
                            context,
                            organisationListResponse[selectedOrgIndex].org_id
                        )
                    },
                )

                DropDownMenu(
                    selectedValue = offName,
                    options = offices,
                    label = "Select Office",
                    onValueChangedEvent = { selectedValue, Index ->
                        offName = selectedValue
                        selectedOfficeIndex = Index
                        getEmployeePositionList(
                            positions,
                            apiService,
                            context,
                            organisationListResponse[selectedOrgIndex].org_id
                        )
                    },
                )

                DropDownMenu(
                    selectedValue = workingPosition,
                    options = positions,
                    label = "Select You Position",
                    onValueChangedEvent = { selectedValue, Index ->
                        workingPosition = selectedValue
                        selectedPositionIndex = Index
                    },
                )

                AppTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    keyboardType = KeyboardType.Email
                )

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
                val scope = rememberCoroutineScope()

                // Sign Up Button
                Button(


                    onClick = {
                        if (name.isEmpty() || employeeId.isEmpty() || mobilenumber.isEmpty() || orgName.isEmpty() || workingPosition.isEmpty() || email.isEmpty() || password.isEmpty()){
                            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                            return@Button
                        }else{
                            // Create an employee object
                            val employee = EmployeeRegistration(
                                Name = name.trim(),
                                EmployeeId = employeeId.trim(),
                                MobileNumber = mobilenumber.trim(),
                                Organization = orgName.trim(),
                                Position = workingPosition.trim(),
                                Email = email.trim(),
                                Password = password.trim(),
                                Org_Id = 1,
                                Office_Id = 1
                            )

                            registerEmployee(employee, apiService, context, navController)
                        }


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

private fun registerEmployee(
    employee: EmployeeRegistration,
    apiService: ApiService?,
    context: Context,
    navController: NavController
) {
    apiService?.employeeRegister(employee)?.enqueue(object : Callback<ApiResponse?> {
        override fun onResponse(call: Call<ApiResponse?>, response: Response<ApiResponse?>) {
            if (response.isSuccessful) {
                val userResponse = response.body()
                Log.d("Reg", "User registered successfully: ${userResponse?.message}")
                Toast.makeText(context, "User registered successfully", Toast.LENGTH_SHORT).show()

                if (userResponse?.status == "success") {

                    //TODO save in sharedPreference

                    navController.navigate(Screen.MainScaffoldScreen.route)
                }

            } else {
                Log.d("Reg", "Registration failed: ${response.message()}")
                Toast.makeText(
                    context,
                    "Registration failed: ${response.message()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
            Log.d("Reg", "Network Error: ${t.message}")
            Toast.makeText(context, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
        }
    })
}

private fun getOrganisationList(
    organizations: MutableList<String>,
    apiService: ApiService?,
    context: Context
) {
    apiService?.getOrganisationList()?.enqueue(object : Callback<OrganisationListResponse?> {
        override fun onResponse(
            call: Call<OrganisationListResponse?>,
            response: Response<OrganisationListResponse?>
        ) {
            if (response.isSuccessful) {
                val userResponse = response.body()
                organisationListResponse = userResponse?.data as MutableList<OrganisationData>
                if (userResponse?.status == "success") {

                    for (item in userResponse.data) {
                        organizations.add(item.org_name)
                    }
                }

            } else {
                Log.d("Reg", "Registration failed: ${response.message()}")
                Toast.makeText(
                    context,
                    "Registration failed: ${response.message()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        override fun onFailure(call: Call<OrganisationListResponse?>, t: Throwable) {
            Log.d("Reg", "Network Error: ${t.message}")
            Toast.makeText(context, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
        }
    })
}

private fun getOfficeList(
    offices: MutableList<String>,
    apiService: ApiService?,
    context: Context,
    orgId: Int
) {

    apiService?.getOfficeList(orgId)?.enqueue(object : Callback<OfficeListResponse?> {
        override fun onResponse(
            call: Call<OfficeListResponse?>,
            response: Response<OfficeListResponse?>
        ) {
            if (response.isSuccessful) {
                val userResponse = response.body()
                if (userResponse?.status == "success") {

                    for (item in userResponse.data) {
                        offices.add(item.office_name)
                    }
                }

            } else {
                Log.d("Reg", "Registration failed: ${response.message()}")
                Toast.makeText(
                    context,
                    "Registration failed: ${response.message()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        override fun onFailure(call: Call<OfficeListResponse?>, t: Throwable) {
            Log.d("Reg", "Network Error: ${t.message}")
            Toast.makeText(context, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
        }
    })
}

private fun getEmployeePositionList(
    positions: MutableList<String>,
    apiService: ApiService?,
    context: Context,
    officeId: Int
) {

    apiService?.getPositionList(officeId)
        ?.enqueue(object : Callback<EmployeePositionListResponse?> {
            override fun onResponse(
                call: Call<EmployeePositionListResponse?>,
                response: Response<EmployeePositionListResponse?>
            ) {
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    if (userResponse?.status == "success") {

                        for (item in userResponse.data) {
                            positions.add(item.position_name)
                        }
                    }

                } else {
                    Log.d("Reg", "Registration failed: ${response.message()}")
                    Toast.makeText(
                        context,
                        "Registration failed: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<EmployeePositionListResponse?>, t: Throwable) {
                Log.d("Reg", "Network Error: ${t.message}")
                Toast.makeText(context, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(navController = NavController(LocalContext.current))
}

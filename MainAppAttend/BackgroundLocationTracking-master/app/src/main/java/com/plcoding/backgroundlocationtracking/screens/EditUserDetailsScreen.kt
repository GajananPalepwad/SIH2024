package com.plcoding.backgroundlocationtracking.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.plcoding.backgroundlocationtracking.R
import com.plcoding.backgroundlocationtracking.components.AppTextField
import com.plcoding.backgroundlocationtracking.navigation.Screen

@Composable
fun EditUserDetailsScreen(navController: NavController) {
    // Remember mutable states for email, mobile number, and name
    var email by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    // Static user details
    val orgName = "None"
    val employeeID = "None"
    val workingPosition = "None"


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Image with Edit Icon
        ProfileImage()
        Spacer(modifier = Modifier.height(40.dp))
        Card(
            shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {



                // User input fields
                UserInputFields(
                    name,
                    mobileNumber,
                    email,
                    onNameChange = { name = it },
                    onMobileChange = { mobileNumber = it },
                    onEmailChange = { email = it })

                // Other details section
                OtherDetailsSection(orgName, employeeID, workingPosition)
                Spacer(modifier = Modifier.height(25.dp))

                // Action buttons
                ActionButtons(navController)


            }
        }
    }
}

@Composable
fun ProfileImage() {
    Box {
        // Profile picture
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(3.dp, MaterialTheme.colorScheme.onSecondary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
            )
        }

        // Edit icon
        IconButton(
            onClick = { /* TODO: Change profile picture */ },
            modifier = Modifier
                .size(30.dp)
                .offset(x = (-5).dp, y = (-5).dp)
                .align(Alignment.BottomEnd)
                .clip(CircleShape),
            colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.background)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Change Profile Photo",
                modifier = Modifier.fillMaxSize(),
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@Composable
fun UserInputFields(
    name: String,
    mobileNumber: String,
    email: String,
    onNameChange: (String) -> Unit,
    onMobileChange: (String) -> Unit,
    onEmailChange: (String) -> Unit
) {
    AppTextField(
        value = name,
        onValueChange = onNameChange,
        label = "Name",
        keyboardType = KeyboardType.Text
    )

    AppTextField(
        value = mobileNumber,
        onValueChange = onMobileChange,
        label = "Mobile Number",
        keyboardType = KeyboardType.Number
    )

    AppTextField(
        value = email,
        onValueChange = onEmailChange,
        label = "Email",
        keyboardType = KeyboardType.Email
    )
}

@Composable
fun OtherDetailsSection(orgName: String, employeeID: String, workingPosition: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Other Details",
            style = typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        DetailText("Organization Name: $orgName")
        Spacer(modifier = Modifier.height(10.dp))
        DetailText("Employee ID: $employeeID")
        Spacer(modifier = Modifier.height(10.dp))
        DetailText("Working Position: $workingPosition")
    }
}

@Composable
fun DetailText(text: String) {
    Text(
        text = text,
        style = typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSecondary,
        fontSize = 20.sp,
        fontWeight = FontWeight.W300
    )
}

@Composable
fun ActionButtons(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { /* TODO: Update Password */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Update Password", color = MaterialTheme.colorScheme.onPrimary)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { navController.navigate(Screen.MainScaffoldScreen.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Save Changes", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

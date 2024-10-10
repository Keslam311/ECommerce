package com.example.ecommerce.presentation.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.ecommerce.data.model.SignUpRequest
import com.example.ecommerce.presentation.viewModel.SignUpViewModel


class SignUpScreen : Screen {
    @Composable
    override fun Content() {
        var email by remember { mutableStateOf(TextFieldValue("")) }
        var password by remember { mutableStateOf(TextFieldValue("")) }
        var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }
        var name by remember { mutableStateOf(TextFieldValue("")) }
        var phone by remember { mutableStateOf(TextFieldValue("")) }
        val viewModel: SignUpViewModel = hiltViewModel()
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        var passwordVisible by remember { mutableStateOf(false) }
        val passwordIcon = if (passwordVisible) Icons.Default.Close else Icons.Default.Done
        var confirmPasswordVisible by remember { mutableStateOf(false) }
        val confirmPasswordIcon = if (confirmPasswordVisible) Icons.Default.Close else Icons.Default.Done

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(text = "Register Account", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Complete your details or continue\n" +
                        "with social media.",
                color = Color.Gray, // Update to appropriate color
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Email TextField
            OutlinedTextField(
                value = email,
                onValueChange = { newEmail -> email = newEmail },
                label = { Text("Email", color = Color.Black) },
                placeholder = { Text("example@email.com", color = Color.Gray) },
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email, // Example icon
                        contentDescription = "Email Icon",
                        tint = Color.Black
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF9800), // Set border color when focused
                    unfocusedBorderColor = Color(0xFFFF9800), // Set border color when not focused
                    cursorColor = Color(0xFFFF9800) // Cursor color
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Password TextField
            OutlinedTextField(
                value = password,
                onValueChange = { newPass -> password = newPass },
                label = { Text("Password", color = Color.Black) },
                placeholder = { Text("********", color = Color.Gray) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                        Icon(
                            imageVector = passwordIcon, // Icon to toggle visibility
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = Color.Black
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF9800), // Set border color when focused
                    unfocusedBorderColor = Color(0xFFFF9800), // Set border color when not focused
                    cursorColor = Color(0xFFFF9800) // Cursor color
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { newPass -> confirmPassword = newPass },
                label = { Text("Confirm Password", color = Color.Black) },
                placeholder = { Text("********", color = Color.Gray) },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        confirmPasswordVisible = !confirmPasswordVisible
                    }) {
                        Icon(
                            imageVector = confirmPasswordIcon, // Icon to toggle visibility
                            contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password",
                            tint = Color.Black
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF9800), // Set border color when focused
                    unfocusedBorderColor = Color(0xFFFF9800), // Set border color when not focused
                    cursorColor = Color(0xFFFF9800) // Cursor color
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { newName -> name = newName },
                label = { Text("Name", color = Color.Black) },
                placeholder = { Text("Your name", color = Color.Gray) },
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person, // Example icon
                        contentDescription = "Person Icon",
                        tint = Color.Black
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF9800), // Set border color when focused
                    unfocusedBorderColor = Color(0xFFFF9800), // Set border color when not focused
                    cursorColor = Color(0xFFFF9800) // Cursor color
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = phone,
                onValueChange = { newPhone -> phone = newPhone },
                label = { Text("Phone", color = Color.Black) },
                placeholder = { Text("", color = Color.Gray) },
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Phone Icon",
                        tint = Color.Black
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF9800), // Set border color when focused
                    unfocusedBorderColor = Color(0xFFFF9800), // Set border color when not focused
                    cursorColor = Color(0xFFFF9800) // Cursor color
                )
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    Log.d(
                        "data",
                        "Email = ${email.text} , password = ${password.text}, phone = ${phone.text} , name = ${name.text}"
                    )
                    viewModel.singUp(
                        SignUpRequest(
                        email = email.text,
                        name = name.text,
                        password = password.text,
                        phone = phone.text
                    ),
                        onSuccess = {
                            Toast.makeText(context, "Sign-up successful", Toast.LENGTH_SHORT).show()
                            navigator.push(LoginScreen())
                        },
                        onError = {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            Log.w("Error", "No Data")
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9800)
                )
            ) {
                Text(text = "Sign Up", color = Color.White, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = " Already have an account? ", color = Color.Black)
                Text(
                    text = "Login",
                    color = Color(0xFFFF9800),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navigator.push(LoginScreen())
                    })
            }
        }
    }
}
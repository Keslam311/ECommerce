package com.example.ecommerce.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.ecommerce.data.model.Addresses
import com.example.ecommerce.presentation.viewModel.GetAddressesViewModel
import com.example.ecommerce.presentation.viewModel.UpdateAddressesViewModel
import com.example.ecommerce.ui.theme.Poppins
import com.example.ecommerce.ui.theme.PrimaryColor
import com.example.ecommerce.ui.theme.SecondaryColor
import com.example.ecommerce.ui.theme.Shapes
class UpdateAddressesScreen(val id : Int,private val addresses: Addresses) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: UpdateAddressesViewModel = hiltViewModel()
        val getViewModel: GetAddressesViewModel = hiltViewModel()




        var name by remember { mutableStateOf(addresses.name) }
        var city by remember { mutableStateOf( addresses.city) }
        var region by remember { mutableStateOf( addresses.region) }
        var details by remember { mutableStateOf(addresses.details) }
        var notes by remember { mutableStateOf( addresses.notes) }
        val latitude by remember { mutableStateOf( addresses.latitude) }
        val longitude by remember { mutableStateOf(addresses.longitude) }
        val navigator=LocalNavigator.currentOrThrow
        val context = LocalContext.current


        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Update Address") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Home")
                        }
                    },
                )
            },
            content = { padding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),

                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Name Input
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .padding(top = 20.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                SecondaryColor,
                                cursorColor = PrimaryColor,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = Shapes.medium,
                            singleLine = true,
                            placeholder = {
                                Text(text = "Name", color = Color.Gray)
                            },
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = Poppins
                            )
                        )

                        // City Input
                        OutlinedTextField(
                            value = city,
                            onValueChange = { city = it },
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .padding(top = 20.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                SecondaryColor,
                                cursorColor = PrimaryColor,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = Shapes.medium,
                            singleLine = true,
                            placeholder = {
                                Text(text = "City", color = Color.Gray)
                            },
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = Poppins
                            )
                        )

                        // Region Input
                        OutlinedTextField(
                            value = region,
                            onValueChange = { region = it },
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .padding(top = 20.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                SecondaryColor,
                                cursorColor = PrimaryColor,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = Shapes.medium,
                            singleLine = true,
                            placeholder = {
                                Text(text = "Region", color = Color.Gray)
                            },
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = Poppins
                            )
                        )

                        // Details Input
                        OutlinedTextField(
                            value = details,
                            onValueChange = { details = it },
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .padding(top = 20.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                SecondaryColor,
                                cursorColor = PrimaryColor,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = Shapes.medium,
                            singleLine = true,
                            placeholder = {
                                Text(text = "Details", color = Color.Gray)
                            },
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = Poppins
                            )
                        )

                        // Notes Input
                        OutlinedTextField(
                            value = notes,
                            onValueChange = { notes = it },
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .padding(top = 20.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                SecondaryColor,
                                cursorColor = PrimaryColor,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = Shapes.medium,
                            singleLine = true,
                            placeholder = {
                                Text(text = "Notes", color = Color.Gray)
                            },
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = Poppins
                            )
                        )
                        Button(
                            onClick = {
                                viewModel.updateAddresses(
                                    id = id,
                                    name = name,
                                    city = city,
                                    region = region,
                                    details = details,
                                    notes = notes,
                                    latitude = latitude.toDoubleOrNull() ?: 0.0,
                                    longitude = longitude.toDoubleOrNull() ?: 0.0,
                                    onSuccessful = {
                                        getViewModel.getAddresses()
                                        Toast.makeText(context, "Address updated successfully!", Toast.LENGTH_SHORT).show()
                                        navigator.pop()
                                    },
                                    onError = {
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            },
                            colors = ButtonDefaults.buttonColors(PrimaryColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .padding(top = 20.dp),
                            contentPadding = PaddingValues(vertical = 14.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 0.dp,
                                pressedElevation = 2.dp
                            ),
                            shape = Shapes.medium
                        ) {
                            Text("Update Address",
                                fontFamily = Poppins,
                                color = SecondaryColor,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        )
    }
}

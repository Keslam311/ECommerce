/*
package com.example.ecommerce.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import com.example.ecommerce.presentation.viewModel.GetAddressesViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.ecommerce.data.model.Addresses
import com.example.ecommerce.presentation.viewModel.DeleteAddressesViewModel
import com.example.ecommerce.ui.theme.Poppins
import com.example.ecommerce.ui.theme.PrimaryColor
import com.example.ecommerce.ui.theme.SecondaryColor
import com.example.ecommerce.ui.theme.Shapes

class GetAddressesScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: GetAddressesViewModel = hiltViewModel()
        val addressesState = viewModel.getAddressesResponse.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
//        viewModel.getAddresses()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Your Addresses") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Settings")
                        }
                    },
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                val addresses = addressesState.value
                if (addresses == null) {
                    CircularProgressIndicator()
                } else if (addresses.status) {
                    val addressList: List<Addresses> = addresses.data.data
                    if (addressList.isEmpty()) {
                        Text(text = "No addresses found.")
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(addressList) { address ->
                                AddressCard(address, address.id)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun AddressCard(address: Addresses, id: Int) {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: GetAddressesViewModel = hiltViewModel()
        val deleteViewModel: DeleteAddressesViewModel = hiltViewModel()
        val context = LocalContext.current



        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navigator.push(UpdateAddressesScreen(id, address)) },
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Name: ${address.name}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "City: ${address.city}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Region: ${address.region}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Details: ${address.details}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Notes: ${address.notes}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(top = 8.dp)  // Add space at the bottom
                        )
                    }
                }

                // Delete Icon
                IconButton(
                    onClick = {
                        deleteViewModel.deleteAddresses(id,
                            onSuccessful = {
                                viewModel.getAddresses()
                                Toast.makeText(
                                    context,
                                    "Address deleted successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onError = {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }
                        ) // Assuming you have a delete method in your ViewModel
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp) // Add some padding for better touch target
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete, // Change to your delete icon
                        contentDescription = "Delete Address",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer // Set appropriate color for the icon
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    navigator.push(ChangePassword())
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
                Text(text = "Change Password",  fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold)
            }
        }
    }
}
*/

package com.example.ecommerce.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import com.example.ecommerce.presentation.viewModel.GetAddressesViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.ecommerce.data.model.Addresses
import com.example.ecommerce.presentation.viewModel.DeleteAddressesViewModel
import com.example.ecommerce.ui.theme.Poppins
import com.example.ecommerce.ui.theme.PrimaryColor
import com.example.ecommerce.ui.theme.SecondaryColor
import com.example.ecommerce.ui.theme.Shapes

class GetAddressesScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: GetAddressesViewModel = hiltViewModel()
        val addressesState = viewModel.getAddressesResponse.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        viewModel.getAddresses()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Your Addresses") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Settings")
                        }
                    },
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                val addresses = addressesState.value
                if (addresses == null) {
                    CircularProgressIndicator()
                } else if (addresses.status) {
                    val addressList: List<Addresses> = addresses.data.data
                    if (addressList.isEmpty()) {
                        Text(text = "No addresses found.")
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),  // Take all available space
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(addressList) { address ->
                                AddressCard(address, address.id)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        navigator.push(NewAddresses())
                    },
                    colors = ButtonDefaults.buttonColors(PrimaryColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 200.dp),  // Padding to keep some space from the bottom
                    contentPadding = PaddingValues(vertical = 14.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 2.dp
                    ),
                    shape = Shapes.medium
                ) {
                    Text(
                        text = "Add New Address",
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    @Composable
    fun AddressCard(address: Addresses, id: Int) {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: GetAddressesViewModel = hiltViewModel()
        val deleteViewModel: DeleteAddressesViewModel = hiltViewModel()
        val context = LocalContext.current

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navigator.push(UpdateAddressesScreen(id, address)) },
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Name: ${address.name}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "City: ${address.city}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Region: ${address.region}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Details: ${address.details}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Notes: ${address.notes}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(top = 8.dp)  // Add space at the bottom
                    )
                }
            }

            // Delete Icon
            IconButton(
                onClick = {
                    deleteViewModel.deleteAddresses(id,
                        onSuccessful = {
                            viewModel.getAddresses()
                            Toast.makeText(
                                context,
                                "Address deleted successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onError = {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    ) // Assuming you have a delete method in your ViewModel
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp) // Add some padding for better touch target
            ) {
                Icon(
                    imageVector = Icons.Default.Delete, // Change to your delete icon
                    contentDescription = "Delete Address",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer // Set appropriate color for the icon
                )
            }
        }
    }
}


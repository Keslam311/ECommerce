package com.example.ecommerce.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.data.model.DataXXXXXX
import com.example.ecommerce.presentation.viewModel.ContactsViewModel

class ContactsScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val contactsViewModel: ContactsViewModel = viewModel()
        val contactsState by contactsViewModel.contacts.collectAsState()
        val isLoading by contactsViewModel.isLoading.collectAsState()
        val context = LocalContext.current

        // Fetch contacts once the composable is displayed
        LaunchedEffect(Unit) {
            contactsViewModel.getContacts(
                onSuccess = { },
                onError = { message ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            )
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Contacts") }
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    when {
                        isLoading -> {
                            // Show loading indicator
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        contactsState?.data?.data.isNullOrEmpty() -> {
                            // Show message when no contacts are available
                            Text(
                                text = "No contacts available",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        else -> {
                            // Display contacts list
                            contactsState?.data?.data?.let { contacts ->
                                ContactsList(contacts = contacts)
                            }
                        }
                    }
                }
            }
        )
    }

    @Composable
    fun ContactsList(contacts: List<DataXXXXXX>) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(contacts) { contact ->
                ContactItem(contact = contact)
            }
        }
    }

    @Composable
    fun ContactItem(contact: DataXXXXXX) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = contact.image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(end = 16.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = contact.value,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Type: ${contact.type}",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

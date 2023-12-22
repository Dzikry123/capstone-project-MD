package com.example.jobharbor.ui.screen.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.jobharbor.R
import com.example.jobharbor.preferences.ProfilePreferences
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {

    val profilePreferences = ProfilePreferences(LocalContext.current)

    val fullName by profilePreferences.fullNameFlow.collectAsState("")
    val email by profilePreferences.emailFlow.collectAsState("")
    val dateOfBirth by profilePreferences.dateOfBirthFlow.collectAsState("")
    val phoneNumber by profilePreferences.phoneNumberFlow.collectAsState("")
    val gender by profilePreferences.genderFlow.collectAsState("")

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    fun onSelectedGenderChange(selectedGender: String) {
        coroutineScope.launch {
            profilePreferences.saveDataToDataStore(fullName, email, dateOfBirth, phoneNumber, selectedGender)
        }
    }


    // Kotak Isi
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp)
            .background(
                color = Color.White,
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Konten pada Kotak Isi
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .offset(y = (-20).dp)
                .verticalScroll(scrollState)

        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                // Background Image
                Image(
                    painter = painterResource(id = R.drawable.bg),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .height(230.dp)
                )


            }

            Column(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .padding(horizontal = 28.dp, vertical = 16.dp)
                    .fillMaxHeight(),
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .offset(y = (-90).dp)
                    ) {
                        // Logo Company
                        Image(
                            painter = painterResource(id = R.drawable.pic2),
                            contentDescription = "Company Logo",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                        )
                    }
                }

                // Judul Pekerjaan
                Text(
                    text = "William Rosewood",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .offset(y = (-80).dp, x = 50.dp),

                    )

                // Asal Lokasi
                Text(
                    text = "Bandung, Jawa Barat",
                    style = TextStyle(
                        fontStyle = FontStyle.Italic,
                        color = Color.Black,
                        textAlign = TextAlign.Center

                    ),
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .offset(y = (-80).dp, x = 105.dp),
                )

                Divider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .offset(y = (-80).dp)
                        .alpha(0.3f),
                )

                // Keterangan
                Text(
                    text = "UI UX designers create the user interface for an app, website, or other interactive media. Their work includes collaborating with product managers and engineers to gather requirements from users before designing ideas that can be communicated using storyboards.",
                    style = TextStyle(
                        fontStyle = FontStyle.Italic,
                        color = Color.Black,
                    ),
                    modifier = Modifier
                        .padding(bottom = 16.dp, start = 24.dp, end = 24.dp)
                        .offset(y = (-60).dp),
                    textAlign = TextAlign.Center
                )

                Column(
                    modifier = Modifier
                        .padding(horizontal = 28.dp, vertical = 16.dp)
                        .fillMaxHeight()
                        .offset(y = (-60).dp),
                ) {
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { value ->
                            coroutineScope.launch {
                                profilePreferences.saveDataToDataStore(value, email, dateOfBirth, phoneNumber, gender)
                            } },
                        label = { Text("FullName") },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { value ->
                            coroutineScope.launch {
                                profilePreferences.saveDataToDataStore(fullName, value, dateOfBirth, phoneNumber, gender)
                            }},
                        label = { Text("Email") },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = dateOfBirth,
                        onValueChange = { value ->
                            coroutineScope.launch {
                                profilePreferences.saveDataToDataStore(fullName, email, value, phoneNumber, gender)
                            } },
                        label = { Text("Date Of Birth") },
                        modifier = Modifier.padding(bottom = 16.dp),
                        leadingIcon = { Icon(Icons.Filled.DateRange, null) },
                    )

                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { value ->
                            coroutineScope.launch {
                                profilePreferences.saveDataToDataStore(fullName, email, dateOfBirth, value, gender)
                            }},
                        label = { Text("Phone Number") }
                    )

                    // RadioButton untuk Gender
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .clip(RoundedCornerShape(8.dp))

                    ) {
                        Box(
                            modifier = Modifier.background(Color.Transparent)
                        ) {
                            Row(
                                modifier = Modifier.padding(end = 16.dp)
                            ) {
                                RadioButton(
                                    selected = gender == "Male",
                                    onClick = { onSelectedGenderChange("Male") },
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = "Male",
                                    modifier = Modifier.padding(vertical = 16.dp,)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Box(
                            modifier = Modifier.background(Color.Transparent)
                        ) {
                            Row(
                                modifier = Modifier.padding(end = 16.dp)
                            ) {
                                RadioButton(
                                    selected = gender == "Female",
                                    onClick = { onSelectedGenderChange("Female") },
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = "Female",
                                    modifier = Modifier.padding(vertical = 16.dp,)
                                )
                            }
                        }
                    }

                    val context = LocalContext.current

                    // Tombol Save
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                profilePreferences.saveDataToDataStore(fullName, email, dateOfBirth, phoneNumber, gender)
                                Toast.makeText(context, "Data Profile Saved Successfully", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Save", color = Color.White)
                    }
                }
            }
        }
    }
    TopBar()
}


@Composable
fun TopBar() {

    // Top Bar
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Arrow Back
            val navController = rememberNavController()
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .padding(8.dp),
                tint = Color.White
            )

            // Judul "Job Detail"
            Text(
                text = "Profile",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                color = Color.White
            )

            Spacer(modifier = Modifier.width(48.dp))
        }
    }
}



@Preview
@Composable
fun TopBarPreview() {
    val navController = rememberNavController()
    Surface(color = Color.LightGray) {
        ProfileScreen()
    }
}


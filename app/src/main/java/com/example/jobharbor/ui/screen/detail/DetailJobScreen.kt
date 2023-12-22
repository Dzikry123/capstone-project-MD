package com.example.jobharbor.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.jobharbor.R
import com.example.jobharbor.ViewModelFactory
import com.example.jobharbor.di.Injection
import com.example.jobharbor.ui.common.UiState

@Composable
    fun DetailJobScreen(
        jobId: Long,
        viewModel: DetailJobViewModel = viewModel(
            factory = ViewModelFactory(
                Injection.provideRepository()
            )
        ),
        navigateBack: () -> Unit,
        onApplyButtonClicked: (String) -> Unit,
    ) {
        val scrollState = rememberScrollState()


        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    viewModel.getJobById(jobId)
                }
                is UiState.Success -> {
                    val data = uiState.data
                    Spacer(modifier = Modifier.height(200.dp))
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
                                .fillMaxSize()
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
                                    painter = painterResource(data.job.bgImg),
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
                                        color = Color.LightGray.copy(alpha = 0.55f),
                                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                                    )
                                    .padding(horizontal = 28.dp, vertical = 16.dp),
                                verticalArrangement = Arrangement.Center
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
                                            painter = painterResource(data.job.logo),
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
                                    text = data.job.jobTitle,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp,
                                        color = Color.Black
                                    ),
                                    modifier = Modifier
                                        .padding(bottom = 8.dp)
                                        .offset(y = (-80).dp)
                                        .align(Alignment.CenterHorizontally),
                                    textAlign = TextAlign.Center
                                )
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        modifier = Modifier,
                                    ) {
                                        Text(
                                            // "Nama Perusahaan - Provinsi, Negara"
                                            text = data.job.companyName,
                                            style = TextStyle(
                                                fontStyle = FontStyle.Italic,
                                                color = colorResource(id = R.color.text_medium)
                                            ),
                                            modifier = Modifier
                                                .offset(y = (-80).dp)
                                                .padding(end = 16.dp),
                                            textAlign = TextAlign.Center
                                        )
                                        // Keterangan
                                        Text(
                                            // "Nama Perusahaan - Provinsi, Negara"
                                            text = data.job.location,
                                            style = TextStyle(
                                                fontStyle = FontStyle.Italic,
                                                  color = colorResource(id = R.color.text_medium)
                                            ),
                                            modifier = Modifier
                                                .padding(bottom = 16.dp)
                                                .offset(y = (-80).dp),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .offset(y = (-80).dp),
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .horizontalScroll(rememberScrollState()),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        data.job.category.forEach {
                                            Box(
                                                modifier = Modifier
                                                    .width(100.dp)
                                                    .height(30.dp)
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .shadow(2.dp, shape = RoundedCornerShape(8.dp))
                                                    .background(Color.White)
                                            ) {
                                                Text(
                                                    text = it,
                                                    modifier = Modifier
                                                        .align(Alignment.Center)
                                                        .padding(bottom = 3.dp),
                                                    fontSize = 12.sp
                                                )
                                            }
                                            Spacer(modifier = Modifier.padding(start = 16.dp))
                                        }
                                    }
                                }

                                // Tab Company Job (Deskripsi, Kebutuhan, Gaji, dan Perusahaan)
                                var selectedTabIndex by remember { mutableStateOf(0) }

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = (-80).dp)
                                ) {
                                    // Daftar Tab
                                    TabRow(
                                        selectedTabIndex = selectedTabIndex,
                                        contentColor = Color.Black,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 16.dp),
                                        indicator = { tabPositions ->
                                            TabRowDefaults.Indicator(
                                                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                                            )
                                        }
                                    ) {
                                        listOf("Description", "Needs", "Salary", "Company").forEachIndexed { index, title ->
                                            Tab(
                                                selected = selectedTabIndex == index,
                                                onClick = { selectedTabIndex = index },
                                                modifier = Modifier.padding(horizontal = 8.dp)
                                            ) {
                                                Text(
                                                    text = title,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier
                                                        .padding(vertical = 8.dp),
                                                    fontSize = 12.sp
                                                )
                                            }
                                        }
                                    }

                                    // Detail Pekerjaan dan Requirement
                                    when (selectedTabIndex) {
                                        0 -> {
                                            // Deskripsi Pekerjaan
                                            Text(
                                                text = "Job Description",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp,
                                                modifier = Modifier.padding(vertical = 8.dp)
                                            )
                                            Text(
                                                text = data.job.description,
                                                color = colorResource(id = R.color.text_medium),
                                                modifier = Modifier.padding(bottom = 16.dp)
                                            )
                                        }
                                        1 -> {
                                            // Kebutuhan Pekerjaan
                                            Text(
                                                text = "Requirement",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp,
                                                modifier = Modifier.padding(vertical = 8.dp)
                                            )
                                            Text(
                                                text = data.job.requirements,
                                                color = colorResource(id = R.color.text_medium),
                                                modifier = Modifier.padding(bottom = 16.dp)
                                            )
                                        }
                                        2-> {
                                            // Gaji
                                            Text(
                                                text = "Salary",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp,
                                                modifier = Modifier.padding(vertical = 8.dp)
                                            )
                                            Text(
                                                text = data.job.salary,
                                                  color = colorResource(id = R.color.text_medium),
                                                modifier = Modifier.padding(bottom = 16.dp)
                                            )
                                        }
                                        3 -> {
                                            // Company Profile
                                            Text(
                                                text = "Company Profile",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp,
                                                modifier = Modifier.padding(vertical = 8.dp)
                                            )
                                            Text(
                                                text = data.job.companyProfile,
                                                  color = colorResource(id = R.color.text_medium),
                                                modifier = Modifier.padding(bottom = 16.dp)
                                            )
                                        }
                                    }
                                }
                                // Button (Apply Now)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .padding(bottom = 16.dp),

                                ) {
                                    val shareMessage = stringResource(
                                        R.string.share_message,
                                        data.job.jobTitle,
                                        data.job.companyName
                                    )
                                    // Button Apply Now
                                    Button(
                                        onClick = {
                                            onApplyButtonClicked(shareMessage)
                                        },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(56.dp)
                                            .padding(end = 8.dp)
                                    ) {
                                        Text(
                                            text = "Apply Now",
                                            color = Color.White
                                        )
                                    }
                                }
                            }





                        }
                    }

                    // Background Image and Top Bar
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
                            val navController = rememberNavController()
                            // Arrow Back
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                modifier = Modifier
                                    .clickable {
                                    }
                                    .padding(8.dp),
                                tint = Color.White
                            )

                            // Judul "Job Detail"
                            Text(
                                text = "Job Detail",
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
                is UiState.Error -> {}
            }
        }



    }





@Preview(showBackground = true)
@Composable
fun DetailJobScreenPreview() {
        DetailJobScreen(jobId = 1, navigateBack = {}, onApplyButtonClicked = {} )

}


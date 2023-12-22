package com.example.jobharbor.ui.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jobharbor.ViewModelFactory
import com.example.jobharbor.di.Injection
import com.example.jobharbor.model.OrderJob
import com.example.jobharbor.ui.common.CompanyJobLayout
import com.example.jobharbor.ui.common.UiState
import com.example.jobharbor.ui.screen.home.HomeViewModel

@Composable
fun SearchScreen(
    navController: NavController,
    goBack: () -> Unit,
    navigateToDetail: (Long) -> Unit
) {
    val viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))



        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    viewModel.getAllJobs()
                }

                is UiState.Success -> {
                    JobListColumnLayout(
                        navController = navController,
                        uiState.data,
                        navigateToDetail
                    )
                }

                is UiState.Error -> {}
            }
        }

    }
}


@Composable
fun JobListColumnLayout(
    navController: NavController,
    jobs: List<OrderJob>,
    navigateToDetail: (Long) -> Unit,
) {
    val filteredList = remember {
        mutableStateOf(listOf<OrderJob>())
    }

    LaunchedEffect(jobs) {
        filteredList.value = jobs
    }
    val searchQuery = remember { mutableStateOf("") }

    Column {
        BasicTextField(
            value = searchQuery.value,
            onValueChange = { newValue ->
                searchQuery.value = newValue
                filteredList.value = if (newValue.isBlank()) {
                    jobs
                } else {
                    jobs.filter {
                        it.job.jobTitle.contains(newValue, ignoreCase = true)
                    }
                }
            },
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 8.dp),
            decorationBox = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color.LightGray.copy(alpha = 0.3f))
                        .padding(horizontal = 16.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.Black.copy(alpha = 0.7f),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f),
                    ) {
                        Text(
                            text = if (searchQuery.value != "") {
                                           searchQuery.value
                            } else {
                                   "Search Here..."
                                   },
                            color = Color.Black.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        // Judul Job List
        Text(
            text = "Job List",
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .offset(y = 10.dp),
            fontSize = 20.sp
        )

        // Teks
        Text(
            text = "",
            color = Color.Blue,
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 8.dp, top = 8.dp)
                .offset(y = (-20).dp)
        )
        if (filteredList.value.isEmpty()) {
            Text("Data tidak ditemukan")
        } else {
            // Looping box-box horizontal
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
            ) {
                items(filteredList.value.size) {
                    val data = filteredList.value[it]
                    Box(
                        modifier = Modifier
                            .size(width = 320.dp, height = 220.dp)
                            .background(Color.LightGray.copy(alpha = 0.55f))
                            .clickable {
                                navigateToDetail(data.job.id)
                            }
                    ) {
                        // Menggunakan CompanyJobLayout di dalam Box
                        CompanyJobLayout(
                            companyLogo = data.job.logo,
                            companyName = data.job.companyName,
                            jobTitle = data.job.jobTitle,
                            jobLocation = data.job.location,
                            jobCategory = data.job.category,
                            jobSalary = data.job.salary,
                        )
                    }
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                }
            }
        }


    }
}



@Preview(showBackground = true)
@Composable
fun PreviewSearchScreen() {
    val navController = rememberNavController()
    SearchScreen(navController = navController, goBack = {}, navigateToDetail = {})
}

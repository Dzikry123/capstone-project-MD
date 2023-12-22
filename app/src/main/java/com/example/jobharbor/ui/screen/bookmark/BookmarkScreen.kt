package com.example.jobharbor.ui.screen.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jobharbor.ViewModelFactory
import com.example.jobharbor.di.Injection
import com.example.jobharbor.ui.common.CompanyJobLayout
import com.example.jobharbor.ui.common.UiState

@Composable
fun BookmarkScreen() {
    val  viewModel: BookmarkViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )

    val navController = rememberNavController()

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedOrderLaptops()
            }
            is UiState.Success -> {
                JobListFavorite(
                    navController,
                    uiState.data,
                    {}
                )
            }
            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobListFavorite(
    navController: NavController,
    state: BookmarkState,
    navigateToDetail: (Long) -> Unit,
) {
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

        // Looping box-box horizontal
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        ) {
            items(state.orderJob.size) {
                val job = state.orderJob[it].job
                Box(
                    modifier = Modifier
                        .size(width = 320.dp, height = 220.dp)
                        .background(Color.LightGray)
                        .clickable {
                            navigateToDetail(job.id)
                        }
                ) {
                    // Menggunakan CompanyJobLayout di dalam Box
                    CompanyJobLayout(
                        companyLogo = job.logo,
                        companyName= job.companyName,
                        jobTitle= job.jobTitle,
                        jobLocation= job.location,
                        jobCategory= job.category,
                        jobSalary = job.salary,
                    )
                }
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
            }
        }


    }
}

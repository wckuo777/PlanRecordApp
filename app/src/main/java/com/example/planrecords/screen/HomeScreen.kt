package com.example.planrecords.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

import androidx.compose.runtime.getValue

import androidx.compose.runtime.remember

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.planrecords.viewmodel.AddPlanViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.planrecords.data.Plan
import com.example.planrecords.viewmodel.HomeViewModel

@Composable
fun PlanRecordsApp() {
    val navController = rememberNavController()

    // 觀察當前導航的 BackStackEntry 狀態
    val backStackEntry by navController.currentBackStackEntryAsState()


    // 依據導航棧狀態來計算 canNavigateBack
    val canNavigateBack = remember(backStackEntry) {
        navController.previousBackStackEntry != null
    }
    // 判斷是否可以返回
    // val canNavigateBack = backStackEntry?.destination?.route != "home_screen"
    // 監聽導航狀態的變化來更新 canNavigateBack 狀態
    // 如果導航棧中有上一頁，則設置 canNavigateBack 為 true
//    LaunchedEffect(navController) {
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            // 如果導航棧中有上一頁，則設置 canNavigateBack 為 true
//            canNavigateBack = navController.previousBackStackEntry != null
//        }
//    }
    val onAddPlanClick = {
        navController.popBackStack("home_screen", inclusive = false)
    }

    val onCancel: (String) -> Unit = { newPlan ->
        navController.popBackStack("home_screen", inclusive = false)
    }
    Scaffold(
        topBar = {
            PlanRecordsTopBar(
                canNavigateBack = canNavigateBack,
                onNavigateUp = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home_screen",
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            composable("home_screen") { HomeScreen(navController) }
            composable("add_plan_screen") {
                AddPlanScreen(
                    onCancel = onCancel,
                    navController = navController
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanRecordsTopBar(canNavigateBack: Boolean, onNavigateUp: () -> Unit) {
    TopAppBar(
        title = { Text(text = "Plan Records") },
        navigationIcon = if (canNavigateBack) {
            {
                IconButton(onClick = { onNavigateUp() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        } else {
            {} // 傳遞空的 Composable lambda
        }
    )
}

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()  // 使用 Hilt 注入 ViewModel
) {
    val planList by viewModel.planList.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_plan_screen") },
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add New Plan"
                    )
                }
            )
        },
        content = { paddingValues ->
            if (planList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text("No plans available")
                }
            } else {
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(planList) { plan ->
                        PlanItem(
                            plan = plan,
                            onDelete = { viewModel.deletePlan(plan.id) }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun PlanItem(plan: Plan, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,  // 默认高度
            pressedElevation = 8.dp,  // 按下时的高度
            focusedElevation = 6.dp   // 获取焦点时的高度
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = plan.name, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Target Count: ${plan.targetCount}")
                Text(text = "Frequency: ${plan.frequency}")
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Plan"
                )
            }
        }
    }
}


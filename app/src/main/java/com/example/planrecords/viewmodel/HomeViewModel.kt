package com.example.planrecords.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planrecords.data.Plan
import com.example.planrecords.data.PlanDao
import com.example.planrecords.data.PlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val planRepository: PlanRepository
) : ViewModel() {
//    靜態 not good
//    private val _planList = MutableStateFlow<List<Plan>>(emptyList())
//    val planList: StateFlow<List<Plan>> = _planList
//
//    init {
//        loadPlans()
//    }
//
//    private fun loadPlans() {
//        viewModelScope.launch {
//            _planList.value = planDao.getAllPlans()
//        }
//    }
val planList: StateFlow<List<Plan>> = planRepository.allPlans
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun deletePlan(planId: Int) {
        viewModelScope.launch {
            planRepository.deletePlan(planId)
            // loadPlans()  // 删除后重新加载列表
        }
    }


}
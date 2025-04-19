package com.example.planrecords.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planrecords.data.Plan
import com.example.planrecords.data.PlanDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val planDao: PlanDao
) : ViewModel() {

    private val _planList = MutableStateFlow<List<Plan>>(emptyList())
    val planList: StateFlow<List<Plan>> = _planList

    init {
        loadPlans()
    }

    private fun loadPlans() {
        viewModelScope.launch {
            _planList.value = planDao.getAllPlans()
        }
    }

    fun deletePlan(planId: Int) {
        viewModelScope.launch {
            planDao.deletePlan(planId)
            loadPlans()  // 删除后重新加载列表
        }
    }
}
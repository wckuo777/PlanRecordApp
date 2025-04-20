package com.example.planrecords.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planrecords.data.Plan
import com.example.planrecords.data.PlanDao
import com.example.planrecords.data.PlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlanViewModel @Inject constructor(
    private val addPlanRepository: PlanRepository
) : ViewModel() {
    fun addPlan(name: String, targetCount: Int, frequency: String) {
        viewModelScope.launch {
            val plan = Plan(name = name, targetCount = targetCount, frequency = frequency)
            addPlanRepository.addPlan(plan)
        }
    }
}
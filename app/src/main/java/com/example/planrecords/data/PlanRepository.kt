package com.example.planrecords.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlanRepository @Inject constructor(
private val planDao: PlanDao
) {
    val allPlans: Flow<List<Plan>> = planDao.getAllPlans()

    suspend fun addPlan(plan: Plan) = planDao.insertPlan(plan)

    suspend fun deletePlan(id: Int) = planDao.deletePlan(id)
}
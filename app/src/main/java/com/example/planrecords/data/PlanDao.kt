package com.example.planrecords.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlanDao {
    @Insert
    suspend fun insertPlan(plan: Plan)

    @Query("SELECT * FROM plans")
    suspend fun getAllPlans(): List<Plan>

    @Query("DELETE FROM plans WHERE id = :id")
    suspend fun deletePlan(id: Int)
}
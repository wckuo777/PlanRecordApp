package com.example.planrecords.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlan(plan: Plan)

    @Query("SELECT * FROM plans")
    fun getAllPlans(): Flow<List<Plan>>

    @Query("DELETE FROM plans WHERE id = :id")
    suspend fun deletePlan(id: Int)
}
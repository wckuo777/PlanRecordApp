package com.example.planrecords.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plans")
data class Plan(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val targetCount: Int,
    val frequency: String
)
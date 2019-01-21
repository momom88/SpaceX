package com.example.marti.spacex.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launches")
data class SpaceX(

    val details: String?,
    var launch: String,
    @PrimaryKey
    val flight_number: Int,
    val is_tentative: Boolean?,
    val launch_date_local: String?,
    val launch_success: Boolean?,
    val launch_year: Int,
    @Embedded
    val rocket: Rocket,
    val mission_name: String?,
    val static_fire_date_unix: Int?,
    val static_fire_date_utc: String?,
    val tbd: Boolean?,
    val tentative_max_precision: String?,
    val upcoming: Boolean?
)




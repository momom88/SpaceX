package com.example.marti.spacex.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marti.spacex.model.SpaceX

@Database(entities = [SpaceX::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase(){
    abstract fun spaceXDao(): SpaceXDao
}
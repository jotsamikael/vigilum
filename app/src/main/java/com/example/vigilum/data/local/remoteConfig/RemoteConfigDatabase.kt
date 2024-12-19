package com.example.vigilum.data.local.remoteConfig

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author JOTSA MIKAEL
 */
@Database(
    entities = [RemoteConfigEntity::class],
    version = 1
)
abstract class RemoteConfigDatabase:RoomDatabase() {
    abstract val remoteConfigDao: RemoteConfigDao
}
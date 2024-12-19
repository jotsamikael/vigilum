package com.example.vigilum.data.local.remoteConfig

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface RemoteConfigDao {
    //Combination of insert and update will insert record if not present in db otherwise will update
    @Upsert
    suspend fun upsertRemoteConfig(remoteConfigEntity: RemoteConfigEntity)

    @Query("SELECT * FROM RemoteConfigEntity WHERE id = :id")
    suspend fun getRemoteConfigById(id: Int): RemoteConfigEntity
}
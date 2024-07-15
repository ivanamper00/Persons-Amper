package com.amper.personapp.data.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.amper.personapp.data.model.PersonEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface PersonDao {

    @Query("SELECT * FROM personEntity LIMIT :limit OFFSET (:page-1)*:limit")
    fun getAll(page: Int, limit: Int): Single<List<PersonEntity>>

    @Upsert
    fun addAllPerson(persons: List<PersonEntity>): List<Long>

}
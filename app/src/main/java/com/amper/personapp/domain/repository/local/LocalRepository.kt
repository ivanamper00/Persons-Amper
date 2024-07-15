package com.amper.personapp.domain.repository.local

import com.amper.personapp.data.model.PersonDto
import com.amper.personapp.data.model.PersonEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface LocalRepository {

    fun getPersonByPage(page: Int, pageCount: Int): Single<List<PersonDto>>

    fun addAllPerson(list: List<PersonEntity>): List<Long>
}
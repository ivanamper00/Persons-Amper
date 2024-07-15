package com.amper.personapp.data.repository.local

import com.amper.personapp.data.model.PersonDto
import com.amper.personapp.data.model.PersonEntity
import com.amper.personapp.data.model.mapToDto
import com.amper.personapp.data.service.PersonDao
import com.amper.personapp.domain.repository.local.LocalRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocalRepositoryImp @Inject constructor(
    private val personDao: PersonDao
) : LocalRepository {

    override fun getPersonByPage(page: Int, pageCount: Int): Single<List<PersonDto>> {
        return personDao.getAll(page, pageCount)
            .map {
                it.map { person -> person.mapToDto() }
            }
    }

    override fun addAllPerson(list: List<PersonEntity>): List<Long> {
        return personDao.addAllPerson(list)
    }

}
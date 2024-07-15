package com.amper.personapp.data.repository

import com.amper.personapp.data.model.PersonDto
import com.amper.personapp.data.model.mapToDto
import com.amper.personapp.data.model.mapToEntity
import com.amper.personapp.domain.repository.local.LocalRepository
import com.amper.personapp.domain.repository.remote.PersonRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataHandler @Inject constructor(
    private val personRepository: PersonRepository,
    private val localRepository: LocalRepository,
) {

    fun getPersonDtoList(page: Int, pageCount: Int): Observable<List<PersonDto>> {
        val remote = personRepository.getPersonList(page, pageCount)
            .map {
                localRepository.addAllPerson(it.map { p -> p.mapToEntity() })
                it.map { p -> p.mapToDto() }
            }
        val local = localRepository.getPersonByPage(page, pageCount)
            .flatMap {
                if (it.isEmpty()) {
                    remote
                } else {
                    Single.just(it)
                }
            }

        return local.toObservable()
    }
}
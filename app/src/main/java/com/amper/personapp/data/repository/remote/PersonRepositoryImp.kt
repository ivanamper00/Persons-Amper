package com.amper.personapp.data.repository.remote

import com.amper.personapp.data.model.PersonModel
import com.amper.personapp.data.service.PersonService
import com.amper.personapp.domain.repository.remote.PersonRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PersonRepositoryImp @Inject constructor(
    private val service: PersonService
) : PersonRepository {

    override fun getPersonList(
        page: Int,
        pageCount: Int
    ): Single<List<PersonModel>> {
        return service.getPersonList(page, pageCount)
            .map { response ->
                response.result ?: mutableListOf()
            }
    }

}
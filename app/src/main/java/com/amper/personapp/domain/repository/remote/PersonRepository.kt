package com.amper.personapp.domain.repository.remote

import com.amper.personapp.data.model.PersonModel
import io.reactivex.rxjava3.core.Single

interface PersonRepository {
    fun getPersonList(
        page: Int,
        pageCount: Int
    ): Single<List<PersonModel>>
}
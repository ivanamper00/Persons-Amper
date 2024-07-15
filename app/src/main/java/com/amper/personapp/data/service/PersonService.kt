package com.amper.personapp.data.service

import com.amper.personapp.data.model.PersonResultModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface PersonService {

    @GET("api/")
    fun getPersonList(
        @Query("page") page: Int,
        @Query("results") result: Int
    ): Single<PersonResultModel>

}
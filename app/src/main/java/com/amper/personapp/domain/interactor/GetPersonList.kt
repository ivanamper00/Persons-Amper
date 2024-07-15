package com.amper.personapp.domain.interactor

import com.amper.personapp.data.repository.DataHandler
import com.amper.personapp.util.SchedulerProvider
import javax.inject.Inject

class GetPersonList @Inject constructor(
    private val dataHandler: DataHandler,
    private val schedulerProvider: SchedulerProvider
) {
    operator fun invoke(page: Int, pageCount: Int) =
        dataHandler.getPersonDtoList(page, pageCount)
            .subscribeOn(schedulerProvider.io)
            .observeOn(schedulerProvider.main)
}
package com.amper.personapp.data.repository

import com.amper.personapp.data.model.Id
import com.amper.personapp.data.model.PersonModel
import com.amper.personapp.data.model.mapToDto
import com.amper.personapp.domain.repository.local.LocalRepository
import com.amper.personapp.domain.repository.remote.PersonRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DataHandlerTest {

    @MockK
    private lateinit var localRepository: LocalRepository

    @MockK
    private lateinit var personRepository: PersonRepository

    private lateinit var dataHandler: DataHandler

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataHandler = DataHandler(personRepository, localRepository)
    }

    @Test
    fun getPersonDtoListFromRemoteSuccess() {
        val pageCount = 10
        val pageNumber = 1
        val data = List(pageCount) { PersonModel(
            id = Id(value = it.toString())
        ) }
        every {
            personRepository.getPersonList(pageNumber,pageCount)
        } returns Single.just(data)

        every {
            localRepository.addAllPerson(any())
        } returns List(10) { it.toLong() }

        every {
            localRepository.getPersonByPage(pageNumber,pageCount)
        } returns Single.just(emptyList())

        val result = dataHandler.getPersonDtoList(pageNumber, pageCount).blockingSingle()

        Assert.assertEquals(data.map { it.mapToDto() }, result)
    }

    @Test
    fun getPersonDtoListFromLocalSuccess() {
        val pageCount = 10
        val pageNumber = 1
        val data = List(pageCount) { PersonModel(
            id = Id(value = it.toString())
        ) }
        every {
            personRepository.getPersonList(pageNumber,pageCount)
        } returns Single.just(emptyList())

        every {
            localRepository.addAllPerson(any())
        } returns emptyList()

        every {
            localRepository.getPersonByPage(pageNumber,pageCount)
        } returns Single.just(data.map { it.mapToDto()})

        val result = dataHandler.getPersonDtoList(pageNumber, pageCount).blockingSingle()

        Assert.assertEquals(data.map { it.mapToDto() }, result)
    }

}
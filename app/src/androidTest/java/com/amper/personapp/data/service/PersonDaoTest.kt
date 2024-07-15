package com.amper.personapp.data.service

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.amper.personapp.data.AppDatabase
import com.amper.personapp.data.model.PersonEntity
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class PersonDaoTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var dao: PersonDao

    @Before
    fun setup() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = appDatabase.dao
    }

    @Test
    fun addAllPersonTest() {
        val personList = listOf(
            PersonEntity(
                id = "123",
                firstName = "firstName",
                lastName = "lastName",
                birthDay = "birthDay",
                email = "email",
                mobileNumber = "cell",
                contactPersonNumber = "phone"
            ),
            PersonEntity(
                id = "asd",
                firstName = "qwe",
                lastName = "lastName",
                birthDay = "birthDay",
                email = "email",
                mobileNumber = "cell",
                contactPersonNumber = "phone"
            )
        )
        dao.addAllPerson(personList)

        val personList1 = dao.getAll(1, 10).blockingGet()
        Assert.assertEquals(personList, personList1)
    }


    @After
    fun teardown() {
        appDatabase.close()
    }
}
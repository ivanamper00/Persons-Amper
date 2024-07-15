package com.amper.personapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.amper.personapp.data.model.Id
import com.amper.personapp.data.model.PersonDto
import com.amper.personapp.data.model.PersonModel
import com.amper.personapp.data.model.mapToDto
import com.amper.personapp.domain.interactor.GetPersonList
import com.amper.personapp.util.NoConnectivityException
import com.amper.personapp.util.UiState
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verifyAll
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: ProfileViewModel

    @MockK
    private lateinit var getPersonList: GetPersonList

    @MockK
    private lateinit var observer: Observer<UiState<List<PersonDto>>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
        viewModel = ProfileViewModel(getPersonList)
    }

    @Test
    fun getPersonsListSuccess() {
        val pageCount = 10
        val data = List(pageCount) { PersonModel(
            id = Id(value = it.toString())
        ) }
        every {
            getPersonList.invoke(1, pageCount)
        } returns Observable.just(data.map { it.mapToDto() })

        val mockedObserver = viewModel.personState.test(observer)

        viewModel.getPersonsList(isRefreshed = true)

        Assert.assertTrue(viewModel.page > 0)
        verifyAll {
            mockedObserver.onChanged(UiState.Loading(true))
            mockedObserver.onChanged(UiState.Success(data.map { it.mapToDto()}))
            mockedObserver.onChanged(UiState.Loading(false))
        }
    }

    @Test
    fun getPersonsListNoInternetConnection() {
        val pageCount = 10
        val error = NoConnectivityException()
        every {
            getPersonList.invoke(1, pageCount)
        } returns Observable.error(error)

        val mockedObserver = viewModel.personState.test(observer)

        viewModel.getPersonsList(isRefreshed = true)

        verifyAll {
            mockedObserver.onChanged(UiState.Loading(true))
            mockedObserver.onChanged(UiState.Error(error))
            mockedObserver.onChanged(UiState.Loading(false))
        }
    }


    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }
}

fun <T> LiveData<T>.test(observer: Observer<T>) = spyk<Observer<T>>(observer)
    .also { this.observeForever(it) }
package com.amper.personapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amper.personapp.data.model.PersonDto
import com.amper.personapp.domain.interactor.GetPersonList
import com.amper.personapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getPersonList: GetPersonList,
): ViewModel() {

    private val _personState = MutableLiveData<UiState<List<PersonDto>>>()
    val personState: LiveData<UiState<List<PersonDto>>> get() = _personState
    private val compositeDisposable = CompositeDisposable()
    var page = 0
        private set

    fun getPersonsList(pageCount: Int = 10, isRefreshed: Boolean = false) {
        if(isRefreshed) page = 0
        compositeDisposable.add(
            getPersonList.invoke(++page, pageCount)
                .doOnSubscribe { _personState.value = UiState.Loading(true) }
                .subscribe(
                    {
                        _personState.value = UiState.Success(it)
                        _personState.value = UiState.Loading(false)
                    },
                    {
                        _personState.value = UiState.Error(it)
                        _personState.value = UiState.Loading(false)
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
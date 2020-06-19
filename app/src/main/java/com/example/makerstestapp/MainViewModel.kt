package com.example.makerstestapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Item
import com.example.data.Result
import com.example.domain.GetItemsUseCase
import com.example.makerstestapp.factList.postListValue
import com.example.makerstestapp.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(val getItemsUseCase: GetItemsUseCase) : ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _isDataLoadingError = MutableLiveData<Boolean>()
    val isDataLoadingError: LiveData<Boolean> = _isDataLoadingError

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    private val _items =
        MutableLiveData<List<Item>>().apply { value = mutableListOf() }
    val items: LiveData<List<Item>> = _items

    private val _totalItemsSize = MutableLiveData<Int>()
    val totalItemsSize: LiveData<Int> = _totalItemsSize

    private val _openDetailsEvent = MutableLiveData<Event<Item>>()
    val openDetailsEvent: LiveData<Event<Item>> = _openDetailsEvent

    private val STARTING_PAGE = 1

    init {
        loadFacts()
    }

    fun loadFacts() {
        _dataLoading.value = true
        viewModelScope.launch {
            val factsResult = getItemsUseCase()
            if (factsResult is Result.Success) {
                _isDataLoadingError.postValue(false)
                _items.postListValue(factsResult.data)
                loadFactsItemsSize()
            } else {
                _isDataLoadingError.postValue(true)
                _items.postListValue(mutableListOf())
                showSnackbarMessage(factsResult.toString())
            }

            _dataLoading.postValue(false)

        }

    }

    fun openDetails(factItemModel: Item) {
        _openDetailsEvent.value = Event(factItemModel)
    }

    fun sortItemsByPrice() {
        _dataLoading.postValue(true)
        viewModelScope.launch {
                var result = getItemsUseCase.getItemsSortedByPrice()
                if (result is Result.Success) {
                    _isDataLoadingError.postValue(false)
                    getItemsUseCase.page = STARTING_PAGE
                    refreshAdapter()
                    _items.postValue(result.data)
                } else {
                    _isDataLoadingError.postValue(true)
                    _items.postValue(mutableListOf())
                    showSnackbarMessage(result.toString())
                }
                _dataLoading.postValue(false)
        }

    }

    fun sortItemsByCategory() {
        _dataLoading.postValue(true)
        viewModelScope.launch {
            var result = getItemsUseCase.getItemsSortedByCategory()
            if (result is Result.Success) {
                _isDataLoadingError.postValue(false)
                getItemsUseCase.page = STARTING_PAGE
                refreshAdapter()
                _items.postValue(result.data.toMutableList())
            } else {
                _isDataLoadingError.postValue(true)
                _items.postValue(mutableListOf())
                showSnackbarMessage(result.toString())
            }
            _dataLoading.postValue(false)
        }

    }

    /**this fun is for ListAdapter, if postValue() immediately it keeps item's position in RecView*/
    private suspend fun refreshAdapter() {
        _items.postValue(emptyList())
        delay(200)
    }

    fun loadFactsItemsSize() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _totalItemsSize.postValue(getItemsUseCase.getFactsItemsSize())
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        _snackbarText.postValue(Event(message))
    }

    override fun onCleared() {
        super.onCleared()
        getItemsUseCase.page = STARTING_PAGE
    }
}

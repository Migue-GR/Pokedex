package com.pokedex.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pokedex.model.enums.PokeError
import com.pokedex.model.enums.Success

object UiEventsManager {
    var shouldShowRemotePokes = true
    var shouldShowLocalPokes = false

    private val mError = MutableLiveData<PokeError>()
    val error = mError as LiveData<PokeError>
    fun showError(error: PokeError) = mError.postValue(error)

    private val mSuccess = MutableLiveData<Success>()
    val success = mSuccess as LiveData<Success>
    fun showSuccess(success: Success) = mSuccess.postValue(success)

    private val mShowLoading = MutableLiveData<Boolean>()
    val showLoading = mShowLoading as LiveData<Boolean>
    fun showLoading(showLoading: Boolean) = mShowLoading.postValue(showLoading)
}
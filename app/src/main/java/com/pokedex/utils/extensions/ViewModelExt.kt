package com.pokedex.utils.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokedex.model.enums.PokeError.SOMETHING_WENT_WRONG
import com.pokedex.utils.UiEventsManager.showError
import com.pokedex.utils.UiEventsManager.showLoading
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

/**
 * Runs a Job in the given [CoroutineContext].
 * @param ctx CoroutineContext to execute the work from (Default is [Dispatchers.IO]).
 * @param block Work to execute.
 */
fun ViewModel.launchUseCase(ctx: CoroutineContext = Dispatchers.IO, block: suspend () -> Unit) =
    viewModelScope.launch(ctx) {
        showLoading(true)
        try {
            block()
        } catch (t: Throwable) {
            Timber.e(t)
            showError(SOMETHING_WENT_WRONG)
        }
        showLoading(false)
    }
package br.com.isgreen.calculatorsimulator.base

import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.isgreen.calculatorsimulator.R
import br.com.isgreen.calculatorsimulator.helper.exception.ExceptionHelper
import kotlinx.coroutines.*
import java.net.HttpURLConnection

/**
 * Created by Éverdes Soares on 02/16/2020.
 */

abstract class BaseViewModel : ViewModel(), BaseContract.ViewModel {

    private val job = SupervisorJob()

    protected val ioScope = CoroutineScope(Dispatchers.IO + job)

    protected val mLoading = MutableLiveData<Boolean>()
    protected val mErrorMessage = MutableLiveData<Any>() // String or Int
    protected val mDestination = MutableLiveData<@IdRes Int>()

    override val loading: LiveData<Boolean>
        get() = mLoading
    override val errorMessage: LiveData<Any>
        get() = mErrorMessage
    override val destination: LiveData<Int>
        get() = mDestination

    protected fun applyDefaultLaunch(
        validatorHelper: BaseValidatorHelper? = null,
        vararg any: Any?,
        block: suspend CoroutineScope.() -> Unit
    ) {
        ioScope.launch {
            try {
                mLoading.postValue(true)

//                validatorHelper?.validate(*any)

                block.invoke(this)
            } catch (exception: Exception) {
                exception.printStackTrace()
                // to force user to login
                if (ExceptionHelper.getCode(exception) == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    mDestination.postValue(R.id.simulatorFragment)
                } else {
                    mErrorMessage.postValue(ExceptionHelper.getMessage(exception))
                }
            } finally {
                mLoading.postValue(false)
            }
        }
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        ioScope.coroutineContext.cancel()
    }
}
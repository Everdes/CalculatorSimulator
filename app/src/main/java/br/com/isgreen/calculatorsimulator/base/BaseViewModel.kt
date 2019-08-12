package br.com.isgreen.calculatorsimulator.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Éverdes Soares on 08/11/2019.
 */

abstract class BaseViewModel : ViewModel() {

    protected val mMessage = MutableLiveData<Int>()
    protected val mLoading = MutableLiveData<Boolean>()

}
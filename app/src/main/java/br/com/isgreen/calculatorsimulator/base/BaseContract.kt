package br.com.isgreen.calculatorsimulator.base

import androidx.lifecycle.LiveData

/**
 * Created by Éverdes Soares on 02/16/2020.
 */

interface BaseContract {

    interface ViewModel {
        val loading: LiveData<Boolean>
        val destination: LiveData<Int>
        val errorMessage: LiveData<Any>
    }

}
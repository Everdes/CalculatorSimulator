package br.com.isgreen.calculatorsimulator.base

/**
 * Created by Éverdes Soares on 09/10/2019.
 */

interface BaseValidatorHelper {

    companion object {
        const val NO_ERROR = -1
    }

    suspend fun validate(vararg any: Any?)

}
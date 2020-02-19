package br.com.isgreen.calculatorsimulator.base

/**
 * Created by Éverdes Soares on 02/17/2020.
 */

class BaseValidationException(
    override val message: String,
    val code: Int? = -1
) : Throwable()
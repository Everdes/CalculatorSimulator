package br.com.isgreen.calculatorsimulator.data.api.helper

import br.com.isgreen.calculatorsimulator.data.api.Api

/**
 * Created by Éverdes Soares on 08/07/2019.
 */

class ApiHelperImpl(private val api: Api) : ApiHelper {

    override suspend fun getSimulation(
        rate: Int,
        index: String,
        isTaxFree: Boolean,
        maturityDate: String,
        investedAmount: Double
    ) = api.getSimulation(rate, index, isTaxFree, maturityDate, investedAmount)

}
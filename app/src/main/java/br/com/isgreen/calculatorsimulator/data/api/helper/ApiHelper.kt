package br.com.isgreen.calculatorsimulator.data.api.helper

import br.com.isgreen.calculatorsimulator.data.model.Simulation

/**
 * Created by Éverdes Soares on 08/07/2019.
 */

interface ApiHelper {

    suspend fun getSimulation(
        rate: Int,
        index: String,
        isTaxFree: Boolean,
        maturityDate: String,
        investedAmount: Double
    ): Simulation

}
package br.com.isgreen.calculatorsimulator.screen.simulator

import br.com.isgreen.calculatorsimulator.data.api.helper.ApiHelper
import br.com.isgreen.calculatorsimulator.data.model.Simulation

/**
 * Created by Éverdes Soares on 08/07/2019.
 */

class SimulatorRepository(private val apiHelper: ApiHelper) : SimulatorContract.Repository {

    override suspend fun getSimulation(
        rate: Int,
        index: String,
        isTaxFree: Boolean,
        maturityDate: String,
        investedAmount: Double
    ): Simulation {
        return apiHelper.getSimulation(rate, index, isTaxFree, maturityDate, investedAmount)
    }
}
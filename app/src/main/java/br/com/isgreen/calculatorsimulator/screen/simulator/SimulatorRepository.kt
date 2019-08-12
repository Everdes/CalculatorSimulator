package br.com.isgreen.calculatorsimulator.screen.simulator

import br.com.isgreen.calculatorsimulator.data.datasource.DataSource
import br.com.isgreen.calculatorsimulator.data.model.Simulation

/**
 * Created by Éverdes Soares on 08/07/2019.
 */

class SimulatorRepository(private val dataSource: DataSource) : SimulatorContract.Repository {

    override suspend fun getSimulation(
        rate: Int,
        index: String,
        isTaxFree: Boolean,
        maturityDate: String,
        investedAmount: Double
    ): Simulation {
        return dataSource.getSimulation(rate, index, isTaxFree, maturityDate, investedAmount)
    }
}
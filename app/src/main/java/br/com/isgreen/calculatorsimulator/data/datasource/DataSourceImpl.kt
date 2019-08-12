package br.com.isgreen.calculatorsimulator.data.datasource

import br.com.isgreen.calculatorsimulator.data.api.Api
import br.com.isgreen.calculatorsimulator.data.model.Simulation

/**
 * Created by Éverdes Soares on 08/07/2019.
 */

class DataSourceImpl(private val api: Api) : DataSource {

    override suspend fun getSimulation(
        rate: Int,
        index: String,
        isTaxFree: Boolean,
        maturityDate: String,
        investedAmount: Double
    ): Simulation {
        return api.getSimulation(
            rate,
            index,
            isTaxFree,
            maturityDate,
            investedAmount
        )
    }

}
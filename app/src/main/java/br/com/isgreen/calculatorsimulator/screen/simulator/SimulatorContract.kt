package br.com.isgreen.calculatorsimulator.screen.simulator

import androidx.lifecycle.LiveData
import br.com.isgreen.calculatorsimulator.base.BaseContract
import br.com.isgreen.calculatorsimulator.data.model.Simulation

/**
 * Created by Éverdes Soares on 08/07/2019.
 */

interface SimulatorContract {

    interface ViewModel : BaseContract.ViewModel {
        val simulation: LiveData<Simulation>

        fun getSimulation(rate: Int?, maturityDate: String?, investedAmount: Double?)
    }

    interface Repository {
        suspend fun getSimulation(
            rate: Int,
            index: String,
            isTaxFree: Boolean,
            maturityDate: String,
            investedAmount: Double
        ): Simulation
    }
}
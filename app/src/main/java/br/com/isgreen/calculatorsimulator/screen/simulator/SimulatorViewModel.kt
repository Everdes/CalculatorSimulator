package br.com.isgreen.calculatorsimulator.screen.simulator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.isgreen.calculatorsimulator.R
import br.com.isgreen.calculatorsimulator.base.BaseViewModel
import br.com.isgreen.calculatorsimulator.data.model.Simulation
import br.com.isgreen.calculatorsimulator.util.DateUtil
import br.com.isgreen.calculatorsimulator.validation.SimulationValidator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Created by Éverdes Soares on 08/07/2019.
 */

class SimulatorViewModel(
    private val repository: SimulatorContract.Repository
) : BaseViewModel(), SimulatorContract.ViewModel {

    companion object {
        const val INDEX_CDI = "CDI"
    }

    private val mSimulation = MutableLiveData<Simulation>()

    override val message: LiveData<Int>
        get() = mMessage

    override val loading: LiveData<Boolean>
        get() = mLoading

    override val simulation: LiveData<Simulation>
        get() = mSimulation

    override fun getSimulation(rate: Int?, maturityDate: String?, investedAmount: Double?) {
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            mLoading.postValue(true)

            try {
                val validation = SimulationValidator.validate(rate, maturityDate, investedAmount)
                if (validation != SimulationValidator.NO_ERROR) {
                    mLoading.postValue(false)
                    mMessage.postValue(validation)
                    return@launch
                }

                val formattedDate = DateUtil.formatDate(
                    maturityDate!!,
                    DateUtil.DATE_FORMAT_LOCAL,
                    DateUtil.DATE_FORMAT_API
                )

                val response = repository.getSimulation(
                    rate!!,
                    INDEX_CDI,
                    true,
                    formattedDate,
                    investedAmount!!
                )

                mLoading.postValue(false)
                mSimulation.postValue(response)
            } catch (e: Throwable) {
                mLoading.postValue(false)
                mMessage.postValue(R.string.failed_request)
            }
        }
    }
}
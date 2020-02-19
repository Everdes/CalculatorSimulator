package br.com.isgreen.calculatorsimulator.screen.simulator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.isgreen.calculatorsimulator.base.BaseValidatorHelper
import br.com.isgreen.calculatorsimulator.base.BaseViewModel
import br.com.isgreen.calculatorsimulator.data.model.Simulation
import br.com.isgreen.calculatorsimulator.helper.exception.ExceptionHelper
import br.com.isgreen.calculatorsimulator.util.DateUtil
import kotlinx.coroutines.launch

/**
 * Created by Éverdes Soares on 08/07/2019.
 */

class SimulatorViewModel(
    private val validatorHelper: BaseValidatorHelper,
    private val repository: SimulatorContract.Repository
) : SimulatorContract.ViewModel, BaseViewModel() {

    companion object {
        const val INDEX_CDI = "CDI"
    }

    override val simulation: LiveData<Simulation>
        get() = mSimulation

    private val mSimulation = MutableLiveData<Simulation>()

    override fun getSimulation(rate: Int?, maturityDate: String?, investedAmount: Double?) {
        ioScope.launch {
            mLoading.postValue(true)

            try {
                validatorHelper.validate(rate, maturityDate, investedAmount)

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
                mErrorMessage.postValue(ExceptionHelper.getMessage(e))
            }
        }
    }
}
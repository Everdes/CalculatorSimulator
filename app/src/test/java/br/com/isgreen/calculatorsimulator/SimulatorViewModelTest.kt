package br.com.isgreen.calculatorsimulator

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.isgreen.calculatorsimulator.data.model.Simulation
import br.com.isgreen.calculatorsimulator.screen.simulator.SimulatorContract
import br.com.isgreen.calculatorsimulator.screen.simulator.SimulatorViewModel
import br.com.isgreen.calculatorsimulator.validation.SimulationValidator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Éverdes Soares on 08/08/2019.
 */

@RunWith(MockitoJUnitRunner::class)
open class SimulatorViewModelTest {

    companion object {
        private const val RATE = 123
        private const val INDEX = "CDI"
        private const val TAX_FREE = false
        private const val INVESTED_AMOUNT = 32323.0
        private const val MATURITY_DATE = "03/03/2023"
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mViewModel: SimulatorContract.ViewModel

    @Mock
    private lateinit var mMessageObserver: Observer<Int>

    @Mock
    private lateinit var mLoadingObserver: Observer<Boolean>

    @Mock
    private lateinit var mSimulationObserver: Observer<Simulation>

    @Mock
    private lateinit var mRepository: SimulatorContract.Repository

    @Mock
    private lateinit var mSimulation: Simulation

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        mViewModel = SimulatorViewModel(mRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getSimulation_success() {
        runBlocking {
            mViewModel.loading.observeForever(mLoadingObserver)
            mViewModel.simulation.observeForever(mSimulationObserver)
            mViewModel.getSimulation(RATE, MATURITY_DATE, INVESTED_AMOUNT)

            `when`(mRepository.getSimulation(anyInt(), anyString(), anyBoolean(), anyString(), anyDouble()))
                .thenReturn(mSimulation)

            launch {
                verify(mLoadingObserver).onChanged(true)

                mRepository.getSimulation(RATE, INDEX, TAX_FREE, MATURITY_DATE, INVESTED_AMOUNT)

                verify(mLoadingObserver).onChanged(false)
                verify(mSimulationObserver).onChanged(mSimulation)
            }
        }
    }

    @Test
    fun getSimulation_fail() {
        runBlocking {
            `when`(mRepository.getSimulation(anyInt(), anyString(), anyBoolean(), anyString(), anyDouble()))
                .thenAnswer { Throwable() }

            mViewModel.loading.observeForever(mLoadingObserver)
            mViewModel.message.observeForever(mMessageObserver)
            mViewModel.getSimulation(RATE, MATURITY_DATE, INVESTED_AMOUNT)

            launch {
                verify(mLoadingObserver).onChanged(true)

                SimulationValidator.validate(RATE, MATURITY_DATE, INVESTED_AMOUNT)

                mRepository.getSimulation(RATE, INDEX, TAX_FREE, MATURITY_DATE, INVESTED_AMOUNT)

                verify(mLoadingObserver).onChanged(false)
                verify(mMessageObserver).onChanged(R.string.failed_request)
            }
        }
    }

    @Test
    fun getSimulation_rateIsNull() {
        runBlocking {
            mViewModel.loading.observeForever(mLoadingObserver)
            mViewModel.message.observeForever(mMessageObserver)
            mViewModel.getSimulation(null, MATURITY_DATE, INVESTED_AMOUNT)

            launch {
                verify(mLoadingObserver).onChanged(true)

                SimulationValidator.validate(null, MATURITY_DATE, INVESTED_AMOUNT)

                verify(mLoadingObserver).onChanged(false)
                verify(mMessageObserver).onChanged(R.string.type_cdi_investment_percent)
            }
        }
    }

    @Test
    fun getSimulation_maturityDateIsNull() {
        runBlocking {
            mViewModel.loading.observeForever(mLoadingObserver)
            mViewModel.message.observeForever(mMessageObserver)
            mViewModel.getSimulation(RATE, null, INVESTED_AMOUNT)

            launch {
                verify(mLoadingObserver).onChanged(true)

                SimulationValidator.validate(RATE, null, INVESTED_AMOUNT)

                verify(mLoadingObserver).onChanged(false)
                verify(mMessageObserver).onChanged(R.string.type_the_maturity_date_investment)
            }
        }
    }

    @Test
    fun getSimulation_investedAmountIsNull() {
        runBlocking {
            mViewModel.loading.observeForever(mLoadingObserver)
            mViewModel.message.observeForever(mMessageObserver)
            mViewModel.getSimulation(RATE, MATURITY_DATE, 0.0)

            launch {
                verify(mLoadingObserver).onChanged(true)

                SimulationValidator.validate(RATE, MATURITY_DATE, 0.0)

                verify(mMessageObserver).onChanged(R.string.type_how_much_you_like_apply)
                verify(mLoadingObserver).onChanged(false)
            }
        }
    }

}
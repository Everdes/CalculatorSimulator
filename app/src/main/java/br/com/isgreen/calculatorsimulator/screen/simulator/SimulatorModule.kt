package br.com.isgreen.calculatorsimulator.screen.simulator

import br.com.isgreen.calculatorsimulator.base.BaseValidatorHelper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Éverdes Soares on 08/07/2019.
 */

val simulatorModule = module {
    factory<BaseValidatorHelper> { SimulatorValidatorHelper(get()) }
    factory<SimulatorContract.Repository> { SimulatorRepository(get()) }
    viewModel { SimulatorViewModel(get(), get()) }
}
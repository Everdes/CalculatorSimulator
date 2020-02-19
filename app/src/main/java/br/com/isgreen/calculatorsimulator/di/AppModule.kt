package br.com.isgreen.calculatorsimulator.di

import br.com.isgreen.calculatorsimulator.data.api.Api
import br.com.isgreen.calculatorsimulator.data.api.helper.ApiHelper
import br.com.isgreen.calculatorsimulator.data.api.helper.ApiHelperImpl
import org.koin.dsl.module

/**
 * Created by Éverdes Soares on 08/07/2019.
 */

val appModule = module {
    single { Api.Factory.create() }
    single<ApiHelper> { ApiHelperImpl(get()) }
}
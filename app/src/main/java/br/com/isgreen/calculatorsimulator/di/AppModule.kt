package br.com.isgreen.calculatorsimulator.di

import br.com.isgreen.calculatorsimulator.data.api.Api
import br.com.isgreen.calculatorsimulator.data.datasource.DataSource
import br.com.isgreen.calculatorsimulator.data.datasource.DataSourceImpl
import org.koin.dsl.module

/**
 * Created by Éverdes Soares on 08/07/2019.
 */

val appModule = module {
    single { Api.Factory.create() }
    single<DataSource> { DataSourceImpl(get()) }
}
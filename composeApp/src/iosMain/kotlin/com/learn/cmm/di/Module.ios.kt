package com.learn.cmm.di

import androidx.room.RoomDatabase
import com.learn.cmm.core.database.getPortfolioDatabaseBuilder
import com.learn.cmm.core.database.portofolio.PortfolioDatabase
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<HttpClientEngine> { Darwin.create() }
    singleOf(::getPortfolioDatabaseBuilder)
        .bind<RoomDatabase.Builder<PortfolioDatabase>>()
}

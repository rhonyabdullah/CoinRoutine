package com.learn.cmm.di

import androidx.room.RoomDatabase
import com.learn.cmm.BuildKonfig
import com.learn.cmm.core.database.portofolio.PortfolioDatabase
import com.learn.cmm.core.database.portofolio.getPortfolioDatabase
import com.learn.cmm.core.network.HttpClientFactory
import com.learn.cmm.coins.data.remote.impl.CoinsRemoteDataSourceImpl
import com.learn.cmm.coins.domain.GetCoinDetailUseCase
import com.learn.cmm.coins.domain.GetCoinListUseCase
import com.learn.cmm.coins.domain.GetCoinPriceHistoryUseCase
import com.learn.cmm.coins.domain.api.CoinsRemoteDataSource
import com.learn.cmm.portofolio.data.PortfolioRepositoryImpl
import com.learn.cmm.portofolio.domain.PortfolioRepository
import com.learn.cmm.portofolio.presentation.PortfolioViewModel
import com.learn.cmm.coins.presentation.CoinListViewModel
import com.learn.cmm.trade.domain.BuyCoinUseCase
import com.learn.cmm.trade.domain.SellCoinUseCase
import com.learn.cmm.trade.presentation.buy.BuyViewModel
import com.learn.cmm.trade.presentation.sell.SellViewModel
import io.kotzilla.sdk.analytics.koin.analytics
import io.ktor.client.HttpClient
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) = startKoin {
    config?.invoke(this)
    analytics {
        setApiKey(BuildKonfig.KOTZILLA_KEY)
        setVersion(BuildKonfig.versionName)
    }
    modules(
        sharedModule,
        platformModule,
    )
}

expect val platformModule: Module

val sharedModule = module {

    // core
    single<HttpClient> { HttpClientFactory.create(get()) }

    // portfolio
    single<PortfolioDatabase> { getPortfolioDatabase(get()) }

    singleOf(::PortfolioRepositoryImpl).bind<PortfolioRepository>()

    single { get<PortfolioDatabase>().portfolioDao() }
    single { get<PortfolioDatabase>().userBalanceDao() }
    viewModel {
        PortfolioViewModel(
            portfolioRepository = get()
        )
    }

    // coins list
    viewModel {
        CoinListViewModel(
            getCoinListUseCase = get(),
            getCoinPriceHistoryUseCase = get()
        )
    }
    singleOf(::GetCoinListUseCase)
    singleOf(::CoinsRemoteDataSourceImpl).bind<CoinsRemoteDataSource>()
    singleOf(::GetCoinDetailUseCase)
    singleOf(::GetCoinPriceHistoryUseCase)
    singleOf(::BuyCoinUseCase)
    singleOf(::SellCoinUseCase)

    viewModel { (coinId: String) ->
        BuyViewModel(get(), get(), get(), coinId = coinId)
    }
    viewModel { (coinId: String) ->
        SellViewModel(get(), get(), get(), coinId = coinId)
    }
}

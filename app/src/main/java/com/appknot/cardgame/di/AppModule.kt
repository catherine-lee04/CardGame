package com.appknot.cardgame.di

import com.appknot.bentleymembership.base.BaseViewModel
import com.appknot.cardgame.viewmodel.MainViewModel
import com.appknot.cardgame.viewmodel.PlayViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 *
 * @author Catherine on 2021-07-14
 */

val appContext = module {
    single(named("appContext")) { androidContext() }
}

val viewModelModule = module {
    viewModel { BaseViewModel() }
    viewModel { MainViewModel() }
    viewModel { PlayViewModel() }
}

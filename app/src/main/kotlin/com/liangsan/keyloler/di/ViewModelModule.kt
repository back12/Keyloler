package com.liangsan.keyloler.di

import com.liangsan.keyloler.presentation.main.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::MainViewModel)
}
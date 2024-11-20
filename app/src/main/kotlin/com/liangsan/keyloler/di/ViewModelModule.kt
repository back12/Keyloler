package com.liangsan.keyloler.di

import com.liangsan.keyloler.presentation.search_index.index.IndexViewModel
import com.liangsan.keyloler.presentation.search_index.search.SearchViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::IndexViewModel)

    viewModelOf(::SearchViewModel)
}
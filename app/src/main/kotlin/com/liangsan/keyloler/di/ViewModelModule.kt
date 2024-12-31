package com.liangsan.keyloler.di

import com.liangsan.keyloler.presentation.home.HomeViewModel
import com.liangsan.keyloler.presentation.login.LoginViewModel
import com.liangsan.keyloler.presentation.profile.profile.ProfileViewModel
import com.liangsan.keyloler.presentation.profile.profile_info.ProfileInfoViewModel
import com.liangsan.keyloler.presentation.search_index.index.IndexViewModel
import com.liangsan.keyloler.presentation.search_index.search.SearchViewModel
import com.liangsan.keyloler.presentation.thread.ThreadViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::IndexViewModel)

    viewModelOf(::SearchViewModel)

    viewModelOf(::LoginViewModel)

    viewModelOf(::ProfileViewModel)

    viewModelOf(::ProfileInfoViewModel)

    viewModelOf(::HomeViewModel)

    viewModelOf(::ThreadViewModel)
}
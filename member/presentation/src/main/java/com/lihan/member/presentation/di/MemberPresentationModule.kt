package com.lihan.member.presentation.di

import com.lihan.member.presentation.member.MemberViewModel
import com.lihan.member.presentation.member.util.QRCodeGenerator
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val memberPresentationModule = module {
    viewModelOf(::MemberViewModel)
    singleOf(::QRCodeGenerator)
}
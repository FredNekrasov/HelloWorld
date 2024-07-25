package com.fredprojects.helloworld.di

import com.fredprojects.helloworld.data.di.DMQualifiers
import com.fredprojects.helloworld.domain.features.fibonacci.useCases.FibonacciUseCase
import com.fredprojects.helloworld.domain.features.inequality.useCases.InequalityUseCase
import com.fredprojects.helloworld.domain.features.jumps.useCases.JDUseCases
import com.fredprojects.helloworld.domain.features.jumps.useCases.crud.*
import com.fredprojects.helloworld.domain.features.pws.useCases.PWUseCases
import com.fredprojects.helloworld.domain.features.pws.useCases.crud.*
import org.koin.core.module.dsl.withOptions
import org.koin.core.qualifier.*
import org.koin.dsl.module

val domainModule = module {
    factory { InequalityUseCase() }.withOptions { qualifier(AppQualifiers.INEQUALITY_USE_CASE) }
    factory { FibonacciUseCase() }.withOptions { qualifier(AppQualifiers.FIBONACCI_USE_CASE) }
    factory {
        PWUseCases(
            getPWs = GetPWUseCase(get(named(DMQualifiers.PW_REPOSITORY))),
            upsert = UpsertPWUseCase(get(_q(DMQualifiers.PW_REPOSITORY))),
            delete = DeletePWUseCase(get(_q(DMQualifiers.PW_REPOSITORY)))
        )
    }.withOptions { qualifier(AppQualifiers.PW_USE_CASE) }
    factory {
        JDUseCases(
            getData = GetJDUseCase(get(named(DMQualifiers.JD_REPOSITORY))),
            upsert = UpsertJDUseCase(get(named(DMQualifiers.JD_REPOSITORY))),
            delete = DeleteJDUseCase(get(named(DMQualifiers.JD_REPOSITORY)))
        )
    }.withOptions { qualifier(AppQualifiers.JD_USE_CASE) }
} 
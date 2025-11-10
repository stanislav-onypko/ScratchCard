package com.example.scratchcard.data.di

import com.example.scratchcard.data.generator.UuidCodeGenerator
import com.example.scratchcard.domain.generator.CodeGenerator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GeneratorModule {

    @Binds
    @Singleton
    abstract fun bindCodeGenerator(
        uuidCodeGenerator: UuidCodeGenerator
    ): CodeGenerator
}
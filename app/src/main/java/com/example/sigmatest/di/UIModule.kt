package com.example.sigmatest.di

import dagger.Module

@Module(
    includes = [
        ViewInjectModule::class
    ]
)
class UiModule { }
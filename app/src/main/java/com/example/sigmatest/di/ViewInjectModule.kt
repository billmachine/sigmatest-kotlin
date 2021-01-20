package com.example.sigmatest.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sigmatest.di.helper.DaggerViewModelFactory
import com.example.sigmatest.di.helper.ViewModelKey
import com.example.sigmatest.ui.MainActivity
import com.example.sigmatest.ui.list.ListFragment
import com.example.sigmatest.ui.list.ListViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ViewInjectModule {

    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory?): ViewModelProvider.Factory?

    @ContributesAndroidInjector
    abstract fun bindListFragment(): ListFragment

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    abstract fun provideListViewModel(listViewModel: ListViewModel?): ViewModel?

}
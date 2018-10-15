package com.sqsong.wanandroid.di.module

import com.sqsong.wanandroid.di.scope.ActivityScope
import com.sqsong.wanandroid.ui.home.HomeActivity
import com.sqsong.wanandroid.ui.home.di.MainModule
import com.sqsong.wanandroid.ui.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributeHomeActivity(): HomeActivity

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun contributeLoginActivity(): LoginActivity

}
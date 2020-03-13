package com.app.memorycardgame

import android.app.Application
import com.app.memorycardgame.di.DI
import com.app.memorycardgame.di.module.AppModule

import timber.log.Timber
import toothpick.Toothpick
import toothpick.configuration.Configuration


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogger()
        initToothpick()
        initAppScope()
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction())
        }
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initAppScope() {
        Toothpick.openScope(DI.APP_SCOPE)
            .installModules(AppModule(applicationContext))
    }
}

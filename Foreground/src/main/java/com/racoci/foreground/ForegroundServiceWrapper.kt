package com.racoci.foreground

import android.app.Service
import android.content.Intent
import android.os.Binder

abstract class ForegroundServiceWrapper<
        ServiceBinder : ForegroundServiceWrapper<ServiceBinder>.ForegroundServiceBinder
        > : Service() {
    companion object {

    }

    open inner class ForegroundServiceBinder: Binder() {
        val foregroundService: ForegroundServiceWrapper<ServiceBinder> = this@ForegroundServiceWrapper.foregroundService
    }

    private val foregroundService get() = this

    override fun onBind(intent: Intent?): ServiceBinder? {
        return onForegroundBind(intent)
    }

    abstract fun onForegroundBind(intent: Intent?): ServiceBinder
}
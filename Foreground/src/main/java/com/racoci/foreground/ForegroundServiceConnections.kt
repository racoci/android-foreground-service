package com.racoci.foreground

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder

fun interface ForegroundServiceConnections<
    FSBinder : ForegroundServiceWrapper<FSBinder>.ForegroundServiceBinder,
    FSType : ForegroundServiceWrapper<FSBinder>
    > : ServiceConnection {
    fun FSBinder.onServiceConnected(service: FSType)
    override fun onServiceConnected(
        componennName: ComponentName?,
        binder: IBinder?
    ) {
        (binder as? FSBinder)?.run {
            (foregroundService as? FSType)?.let {
                onServiceConnected(it)
            }
        }
    }

    override fun onServiceDisconnected(componentName: ComponentName?) {

    }
}
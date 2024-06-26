package com.racoci.foreground

import android.content.Context
import android.content.Intent
import android.os.Build
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Executor

interface ForegroundService<
    FSBinder : ForegroundServiceWrapper<FSBinder>.ForegroundServiceBinder,
    FSType : ForegroundServiceWrapper<FSBinder>
    > {

    companion object {
        inline operator fun <
            FSBinder : ForegroundServiceWrapper<FSBinder>.ForegroundServiceBinder,
            reified FSType : ForegroundServiceWrapper<FSBinder>
        > invoke(
            context: Context,
            flags: Int,
            executor: Executor,
            connection: ForegroundServiceConnections<FSBinder, FSType>
        ) {
            val intent = Intent(context, FSType::class.java)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                context.bindService(intent, flags, executor, connection)
            } else {
                context.bindService(intent, connection, flags)
            }
        }

        inline operator fun <
            FSBinder : ForegroundServiceWrapper<FSBinder>.ForegroundServiceBinder,
            reified FSType : ForegroundServiceWrapper<FSBinder>
        > invoke(
            service: FSType,
            binder: FSBinder
        ) = object: ForegroundService<FSBinder, FSType> {
            override val service: FSType
                get() = service
            override val binder: FSBinder
                get() = binder

        }

        suspend inline fun <
            FSBinder : ForegroundServiceWrapper<FSBinder>.ForegroundServiceBinder,
            reified FSType : ForegroundServiceWrapper<FSBinder>
            > invoke(
            context: Context,
            flags: Int,
            executor: Executor
        ): ForegroundService<FSBinder, FSType> = suspendCancellableCoroutine {
            ForegroundService<FSBinder, FSType>(context, flags, executor) {
                ForegroundService<FSBinder, FSType>(it, this)
            }
        }
    }

    val service: FSType
    val binder: FSBinder
}
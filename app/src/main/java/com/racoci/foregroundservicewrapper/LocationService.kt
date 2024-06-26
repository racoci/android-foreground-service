package com.racoci.foregroundservicewrapper

import android.content.Intent
import android.os.Binder
import com.racoci.foreground.ForegroundServiceWrapper

class LocationService : ForegroundServiceWrapper<LocationService.LocationServiceBinder>() {
    override fun onForegroundBind(intent: Intent?) = LocationServiceBinder()
    inner class LocationServiceBinder :
        ForegroundServiceWrapper<LocationService.LocationServiceBinder>.ForegroundServiceBinder() {

    }
}
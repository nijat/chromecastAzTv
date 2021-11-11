package com.az.channel

import android.app.Application
import com.az.channel.manager.PlaylistManager


/**
 * Created by Enzo Lizama Paredes on 2019-10-21.
 * Contact: lizama.enzo@gmail.com
 */
class App : Application() {
    val playlistManager: PlaylistManager by lazy { PlaylistManager(this) }
    override fun onCreate() {
        super.onCreate()
    }
}
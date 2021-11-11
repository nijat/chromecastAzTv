import com.devbrackets.android.exomediademo.service.MediaImageProvider
import com.devbrackets.android.playlistcore.components.playlisthandler.DefaultPlaylistHandler
import com.devbrackets.android.playlistcore.components.playlisthandler.PlaylistHandler
import com.devbrackets.android.playlistcore.service.BasePlaylistService
import com.az.channel.App
import com.az.channel.m3u.ChannelItem
import com.az.channel.manager.PlaylistManager
import com.az.channel.playlist.AudioApi

/**
 * A simple service that extends [BasePlaylistService] in order to provide
 * the application specific information required.
 */
class MediaService : BasePlaylistService<ChannelItem, PlaylistManager>() {

    override val playlistManager by lazy { (applicationContext as App).playlistManager }

    override fun onCreate() {
        super.onCreate()

        // Adds the audio player implementation, otherwise there's nothing to play media with
        playlistManager.mediaPlayers.add(AudioApi(applicationContext))
    }

    override fun onDestroy() {
        super.onDestroy()

        // Releases and clears all the MediaPlayersMediaImageProvider
        playlistManager.mediaPlayers.forEach {
            it.release()
        }

        playlistManager.mediaPlayers.clear()
    }

    override fun newPlaylistHandler(): PlaylistHandler<ChannelItem> {
        val imageProvider = MediaImageProvider(applicationContext) {
            playlistHandler.updateMediaControls()
        }

        return DefaultPlaylistHandler.Builder(
            applicationContext,
            javaClass,
            playlistManager,
            imageProvider
        ).build()
    }
}
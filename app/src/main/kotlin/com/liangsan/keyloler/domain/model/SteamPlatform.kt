package com.liangsan.keyloler.domain.model

sealed interface SteamPlatform {
    data object SteamPlay : SteamPlatform
    data object Win : SteamPlatform
    data object Mac : SteamPlatform
    data object Linux : SteamPlatform
    data object StreamingVideo : SteamPlatform
    data object StreamingVideoSeries : SteamPlatform
    data object Streaming360Video : SteamPlatform
    data object HTCVive : SteamPlatform
    data object OculusRift : SteamPlatform
    data object WindowsMR : SteamPlatform
    data object ValveIndex : SteamPlatform
    data object Music : SteamPlatform

    companion object {
        fun fromString(str: String): SteamPlatform {
            return when (str) {
                "steamplay" -> SteamPlay
                "win" -> Win
                "mac" -> Mac
                "linux" -> Linux
                "streamingvideo" -> StreamingVideo
                "streamingvideoseries" -> StreamingVideoSeries
                "streaming360video" -> Streaming360Video
                "htcvive" -> HTCVive
                "oculusrift" -> OculusRift
                "windowsmr" -> WindowsMR
                "valveindex" -> ValveIndex
                "music" -> Music
                else -> throw RuntimeException("Can't find corresponding platform, input is $str.")
            }
        }
    }
}
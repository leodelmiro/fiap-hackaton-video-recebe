package com.leodelmiro.recebevideo.core.dataprovider

import com.leodelmiro.recebevideo.core.domain.Video

interface PublicaVideoProcessadoGateway {
    fun executar(video: Video, videoKey: String)
}
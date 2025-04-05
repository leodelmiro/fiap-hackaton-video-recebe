package com.leodelmiro.recebevideo.core.dataprovider

import com.leodelmiro.recebevideo.core.domain.Video

interface UploadVideoGateway {
    suspend fun executar(video: Video)
}
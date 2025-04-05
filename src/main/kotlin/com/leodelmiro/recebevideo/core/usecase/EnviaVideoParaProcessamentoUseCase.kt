package com.leodelmiro.recebevideo.core.usecase

import com.leodelmiro.recebevideo.core.domain.Video

interface EnviaVideoParaProcessamentoUseCase {
    suspend fun executar(video: Video)
}
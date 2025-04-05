package com.leodelmiro.recebevideo.core.usecase.impl

import com.leodelmiro.recebevideo.core.dataprovider.UploadVideoGateway
import com.leodelmiro.recebevideo.core.domain.Video
import com.leodelmiro.recebevideo.core.usecase.EnviaVideoParaProcessamentoUseCase

class EnviaVideoParaProcessamentoUseCaseImpl(private val uploadVideoGateway: UploadVideoGateway) :
    EnviaVideoParaProcessamentoUseCase {
    override suspend fun executar(video: Video) {
        uploadVideoGateway.executar(video)
    }
}
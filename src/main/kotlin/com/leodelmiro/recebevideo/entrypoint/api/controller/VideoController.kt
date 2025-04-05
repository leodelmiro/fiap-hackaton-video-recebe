package com.leodelmiro.recebevideo.entrypoint.api.controller

import com.leodelmiro.recebevideo.core.domain.Video
import com.leodelmiro.recebevideo.core.usecase.EnviaVideoParaProcessamentoUseCase
import com.leodelmiro.recebevideo.entrypoint.api.utils.TokenDecoder.decodeUsername
import org.springframework.web.multipart.MultipartFile

object VideoController {

    suspend fun enviar(
        arquivo: MultipartFile,
        nome: String,
        descricao: String,
        token: String,
        enviaVideoParaProcessamentoUseCase: EnviaVideoParaProcessamentoUseCase
    ) = enviaVideoParaProcessamentoUseCase.executar(Video(arquivo, nome, descricao, decodeUsername(token)))
}

package com.leodelmiro.recebevideo.dataprovider.request

import com.leodelmiro.recebevideo.core.domain.Video

data class NovoVideoRecebidoRequest(
    val nome: String,
    val descricao: String,
    val autor: String,
    val videoKey: String
) {
    constructor(video: Video, videoKey: String) : this(video.nome, video.descricao, video.autor, videoKey)
}
package com.leodelmiro.recebevideo.core.domain

import org.springframework.web.multipart.MultipartFile

class Video(
    val arquivo: MultipartFile,
    val nome: String,
    val descricao: String,
    val autor: String
)
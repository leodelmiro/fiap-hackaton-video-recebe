package com.leodelmiro.recebevideo.entrypoint.api.controller

import com.leodelmiro.recebevideo.core.domain.Video
import com.leodelmiro.recebevideo.core.usecase.EnviaVideoParaProcessamentoUseCase
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.springframework.mock.web.MockMultipartFile
import kotlin.test.assertEquals

class VideoControllerTest {

    private val enviaVideoParaProcessamentoUseCase: EnviaVideoParaProcessamentoUseCase =
        mock(EnviaVideoParaProcessamentoUseCase::class.java)

    @Test
    fun `deve enviar video para processamento`() = runBlocking {
        val arquivo = MockMultipartFile("arquivo", "video.mp4", "video/mp4", ByteArray(10))
        val nome = "video.mp4"
        val descricao = "Descricao do video"
        val token =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InRlc3R1c2VyIiwic3ViIjoiMTIzNDU2Nzg5MCIsIm5hbWUiOiJKb2huIERvZSIsImlhdCI6MTUxNjIzOTAyMn0.jvCvbPXc27QMKIqwwT9QpkY7iD0Nw6XRe1mAqJ-CgJw"
        val username = "testuser"

        val videoEsperado = Video(arquivo, nome, descricao, username)

        val resultado = VideoController.enviar(
            arquivo = arquivo,
            nome = nome,
            descricao = descricao,
            token = token,
            enviaVideoParaProcessamentoUseCase = enviaVideoParaProcessamentoUseCase
        )

        verify(enviaVideoParaProcessamentoUseCase).executar(videoEsperado)
        assertEquals(Unit, resultado)
    }
}
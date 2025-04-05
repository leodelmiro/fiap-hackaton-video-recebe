package com.leodelmiro.recebevideo.entrypoint.api

import com.leodelmiro.recebevideo.core.usecase.EnviaVideoParaProcessamentoUseCase
import com.leodelmiro.recebevideo.dataprovider.VerificaNomeArquivoDoUsuarioGateway
import com.leodelmiro.recebevideo.entrypoint.api.controller.VideoController
import com.leodelmiro.recebevideo.entrypoint.api.response.EnviaVideoResponse
import com.leodelmiro.recebevideo.entrypoint.api.shared.GlobalControllerAdvice.ErrorResponse
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockMultipartFile

class RecebimentoVideoApiTest {

    private val enviaVideoParaProcessamentoUseCase: EnviaVideoParaProcessamentoUseCase = mock(EnviaVideoParaProcessamentoUseCase::class.java)
    private val verificaNomeArquivoDoUsuarioGateway: VerificaNomeArquivoDoUsuarioGateway = mock(VerificaNomeArquivoDoUsuarioGateway::class.java)

    private val recebimentoVideoApi = RecebimentoVideoApi(
        enviaVideoParaProcessamentoUseCase,
        verificaNomeArquivoDoUsuarioGateway
    )

    @Test
    fun `deve retornar erro ao enviar arquivo vazio`() = runBlocking {
        val arquivo = MockMultipartFile("arquivo", ByteArray(0))
        val nome = "video.mp4"
        val descricao = "Descricao do video"
        val token = "Bearer token"

        val response = recebimentoVideoApi.envia(arquivo, nome, descricao, token)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals("Arquivo Vazio", (response.body as ErrorResponse).message)
    }

    @Test
    fun `deve retornar erro ao enviar arquivo com content type invalido`() = runBlocking {
        val arquivo = MockMultipartFile("arquivo", "video.txt", "text/plain", ByteArray(10))
        val nome = "video.mp4"
        val descricao = "Descricao do video"
        val token = "Bearer token"

        val response = recebimentoVideoApi.envia(arquivo, nome, descricao, token)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals("Content Type Inválido", (response.body as ErrorResponse).message)
    }

    @Test
    fun `deve retornar erro ao enviar arquivo com nome duplicado`() = runBlocking {
        val arquivo = MockMultipartFile("arquivo", "video.mp4", "video/mp4", ByteArray(10))
        val nome = "video.mp4"
        val descricao = "Descricao do video"
        val token = "Bearer token"

        `when`(verificaNomeArquivoDoUsuarioGateway.verificaEnvio(nome, token)).thenReturn(true)

        val response = recebimentoVideoApi.envia(arquivo, nome, descricao, token)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals("Nome já existente para arquivo deste usuário", (response.body as ErrorResponse).message)
    }

    @Test
    fun `deve enviar video com sucesso`() = runBlocking {
        val arquivo = MockMultipartFile("arquivo", "video.mp4", "video/mp4", ByteArray(10))
        val nome = "video.mp4"
        val descricao = "Descricao do video"
        val token =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InRlc3R1c2VyIiwic3ViIjoiMTIzNDU2Nzg5MCIsIm5hbWUiOiJKb2huIERvZSIsImlhdCI6MTUxNjIzOTAyMn0.jvCvbPXc27QMKIqwwT9QpkY7iD0Nw6XRe1mAqJ-CgJw"

        `when`(verificaNomeArquivoDoUsuarioGateway.verificaEnvio(nome, token)).thenReturn(false)

        val response = recebimentoVideoApi.envia(arquivo, nome, descricao, token)

        assertEquals(HttpStatus.ACCEPTED, response.statusCode)
        assertEquals(EnviaVideoResponse::class.java, response.body!!::class.java)
        verify(verificaNomeArquivoDoUsuarioGateway).verificaEnvio(nome, token)
        verifyNoMoreInteractions(verificaNomeArquivoDoUsuarioGateway)
    }
}
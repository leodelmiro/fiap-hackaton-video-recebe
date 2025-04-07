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
            "Bearer eyJraWQiOiJQbXZWWlBPT1wvbWZuS3lNUlBpU1NJVXNQOUFINnVZRUF5WjRCQ0tMOEVsTT0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI2NGY4NzQyOC04MDQxLTcwNjEtZGIyMy05YmU3NWZmNmZhY2QiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9qZ0NUWW9TaDYiLCJjb2duaXRvOnVzZXJuYW1lIjoiam9obmRvZSIsIm9yaWdpbl9qdGkiOiIyMzE1MGU2NS05OGUxLTQxNDMtOTgxYS02OTQ1ODQzYzQwZjkiLCJhdWQiOiIzZDRhZm40dmpsbDg5N3YyaG5iYTFrc3NmbiIsImV2ZW50X2lkIjoiOWQxNTAzNmItNjEzMy00MzE4LWFlYTAtMmNjNzlkYWIwMWVjIiwidG9rZW5fdXNlIjoiaWQiLCJhdXRoX3RpbWUiOjE3NDQwNDk4ODUsImV4cCI6MTc0NDA1MzQ4NSwiaWF0IjoxNzQ0MDQ5ODg1LCJqdGkiOiI5OGVjNjQyYy0yM2NlLTQwMjYtYjUyYy0wMTZmMzQ0YmU2YzYiLCJlbWFpbCI6ImpvaG5kb2VAZXhhbXBsZS5jb20ifQ.AvH9oDEXTHwGfkvIgAOy4_rdxiZkQvH10B3qWCNBZ_PAoW1ZET_TsSGsN3byRj53nFwIIK961oiGfh1aQhjp44sSX8Yapt1kyeKE50QptOBJRQNCGLBbc2BSLuivVsJdFGtHREPqST3KUONfWlEfDx6BQnw2xIgcIGcHN-7LyzM2rlu0Zbgmy-yHkMVOUx5HjZBKEBsEbJ1-vo1mA00BjCZIf-0rLFaO1a_o66H8bcL3nI-o8pC2S3OJ_8CnnG0O-ELK64loh71exeDFgmOK7SEgikZ5mXaQH2YGiosG74L2lNQcoBVTnvsm8vzN8_s5g-y9q03erqGsJfWPeJfEEw"
        val username = "johndoe"

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
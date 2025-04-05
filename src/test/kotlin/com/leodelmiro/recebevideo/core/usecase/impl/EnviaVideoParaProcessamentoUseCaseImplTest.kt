package com.leodelmiro.recebevideo.core.usecase.impl

import com.leodelmiro.recebevideo.core.dataprovider.UploadVideoGateway
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import utils.criaVideo

class EnviaVideoParaProcessamentoUseCaseImplTest {

    private val uploadVideoGateway = mock(UploadVideoGateway::class.java)
    private val enviaVideoParaProcessamentoUseCase = EnviaVideoParaProcessamentoUseCaseImpl(uploadVideoGateway)

    @Test
    fun `deve chamar o gateway para enviar o video`() = runBlocking {
        val video = criaVideo()

        enviaVideoParaProcessamentoUseCase.executar(video)

        verify(uploadVideoGateway).executar(video)
    }
}
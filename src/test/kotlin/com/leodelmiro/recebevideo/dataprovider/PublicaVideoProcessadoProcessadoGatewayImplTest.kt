package com.leodelmiro.recebevideo.dataprovider

import com.fasterxml.jackson.databind.ObjectMapper
import com.leodelmiro.recebevideo.dataprovider.request.NovoVideoRecebidoRequest
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.PublishRequest
import utils.criaVideo

class PublicaVideoProcessadoProcessadoGatewayImplTest {

    private val snsClient: SnsClient = mock(SnsClient::class.java)
    private val objectMapper: ObjectMapper = ObjectMapper()
    private val topicArn = "arn:aws:sns:region:account-id:novo-video-recebido"
    private val publicaVideoProcessadoGateway = PublicaVideoProcessadoProcessadoGatewayImpl(
        snsClient = snsClient,
        novoVideoRecebido = topicArn,
        objectMapper = objectMapper
    )

    @Test
    fun `deve publicar mensagem no SNS com os dados corretos`() {
        val video = criaVideo()
        val videoKey = "video-key"
        val expectedRequest = NovoVideoRecebidoRequest(video, videoKey)
        val expectedMessage = objectMapper.writeValueAsString(expectedRequest)

        publicaVideoProcessadoGateway.executar(video, videoKey)

        verify(snsClient).publish(
            PublishRequest.builder()
                .message(expectedMessage)
                .topicArn(topicArn)
                .build()
        )
    }
}
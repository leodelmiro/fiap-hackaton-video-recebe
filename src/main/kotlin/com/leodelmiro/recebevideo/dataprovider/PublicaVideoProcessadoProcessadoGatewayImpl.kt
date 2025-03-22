package com.leodelmiro.recebevideo.dataprovider

import com.fasterxml.jackson.databind.ObjectMapper
import com.leodelmiro.recebevideo.core.dataprovider.PublicaVideoProcessadoGateway
import com.leodelmiro.recebevideo.core.domain.Video
import com.leodelmiro.recebevideo.dataprovider.request.NovoVideoRecebidoRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.PublishRequest

@Component
class PublicaVideoProcessadoProcessadoGatewayImpl(
    private val snsClient: SnsClient,
    @Value("\${amazon.sns.novo-video-recebido}")
    private val novoVideoRecebido: String? = null,
    private val objectMapper: ObjectMapper,
) : PublicaVideoProcessadoGateway {

    override fun executar(video: Video, videoKey: String) {
        val request = PublishRequest.builder()
            .message(objectMapper.writeValueAsString(NovoVideoRecebidoRequest(video, videoKey)))
            .topicArn(novoVideoRecebido)
            .build()
        snsClient.publish(request)
    }
}
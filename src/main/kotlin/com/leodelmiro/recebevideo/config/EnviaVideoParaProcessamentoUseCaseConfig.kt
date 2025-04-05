package com.leodelmiro.recebevideo.config

import com.leodelmiro.recebevideo.core.usecase.impl.EnviaVideoParaProcessamentoUseCaseImpl
import com.leodelmiro.recebevideo.dataprovider.UploadVideoGatewayImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EnviaVideoParaProcessamentoUseCaseConfig {
    @Bean
    fun enviaVideoParaProcessamentoUseCase(
        uploadVideoGatewayImpl: UploadVideoGatewayImpl
    ): EnviaVideoParaProcessamentoUseCaseImpl {
        return EnviaVideoParaProcessamentoUseCaseImpl(uploadVideoGatewayImpl)
    }
}
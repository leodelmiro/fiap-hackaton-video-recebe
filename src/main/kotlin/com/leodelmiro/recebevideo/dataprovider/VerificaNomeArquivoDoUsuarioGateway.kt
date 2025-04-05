package com.leodelmiro.recebevideo.dataprovider

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "enviosClient",
    url = "\${external-apis.envios.url}"
)
interface VerificaNomeArquivoDoUsuarioGateway {
    @GetMapping("envios/verifica")
    fun verificaEnvio(
        @RequestParam("nome") nome: String,
        @RequestHeader("Authorization") token: String,
    ): Boolean
}

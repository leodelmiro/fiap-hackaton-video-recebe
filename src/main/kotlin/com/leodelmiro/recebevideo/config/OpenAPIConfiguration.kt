package com.leodelmiro.recebevideo.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPIConfiguration {
    private val devUrl = "localhost:8080"

    @Bean
    fun myOpenAPI(): OpenAPI {
        val devServer = Server().url(devUrl).description("Server URL para ambiente de Dev")

        val contact =
            Contact().email("leodelmiroms@gmail.com").name("Leonardo Delmiro").url("https://github.com/leodelmiro")

        val mitLicense = License().name("MIT License").url("https://choosealicense.com/licenses/mit/")

        val info = Info()
            .title("Hackaton - Recebimento de Video")
            .version("1.0")
            .contact(contact)
            .description("A API expõe endpoints para o Hackathon de Videos da App de Recebimento de Video.")
            .license(mitLicense)

        return OpenAPI().info(info).servers(listOf(devServer))
    }
}
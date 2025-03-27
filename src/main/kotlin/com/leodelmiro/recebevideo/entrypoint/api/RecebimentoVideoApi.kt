package com.leodelmiro.recebevideo.entrypoint.api

import com.leodelmiro.recebevideo.core.usecase.EnviaVideoParaProcessamentoUseCase
import com.leodelmiro.recebevideo.dataprovider.VerificaNomeArquivoDoUsuarioGateway
import com.leodelmiro.recebevideo.entrypoint.api.controller.VideoController
import com.leodelmiro.recebevideo.entrypoint.api.response.EnviaVideoResponse
import com.leodelmiro.recebevideo.entrypoint.api.shared.GlobalControllerAdvice.ErrorResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@Tag(name = "Videos", description = "Endpoints relacionados ao Recebimento de Video")
@RestController
@RequestMapping("/api/v1/videos")
class RecebimentoVideoApi(
    private val enviaVideoParaProcessamentoUseCase: EnviaVideoParaProcessamentoUseCase,
    private val verificaNomeArquivoDoUsuarioGateway: VerificaNomeArquivoDoUsuarioGateway
) {

    @Operation(
        summary = "Envia Video",
        description = "Envia video para processamento"
    )
    @ApiResponses(value = [ApiResponse(responseCode = "202", description = "Video recebido com sucesso")])
    @PostMapping
    suspend fun envia(
        @RequestParam("arquivo") arquivo: MultipartFile,
        @RequestParam("nome") nome: String,
        @RequestParam("descricao") descricao: String,
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<Any> {
        if (arquivo.isEmpty) {
            return ResponseEntity.badRequest().body(ErrorResponse(HttpStatus.BAD_REQUEST.reasonPhrase, "Arquivo Vazio"))
        }
        if (arquivo.contentType?.contains("video") != true) {
            return ResponseEntity.badRequest()
                .body(ErrorResponse(HttpStatus.BAD_REQUEST.reasonPhrase, "Content Type Inválido"))
        }
        if (verificaNomeArquivoDoUsuarioGateway.verificaEnvio(nome, token)) {
            return ResponseEntity.badRequest().body(
                ErrorResponse(
                    HttpStatus.BAD_REQUEST.reasonPhrase,
                    "Nome já existente para arquivo deste usuário"
                )
            )
        }

        VideoController.enviar(arquivo, nome, descricao, token, enviaVideoParaProcessamentoUseCase)
        return ResponseEntity.accepted().body(EnviaVideoResponse())
    }
}
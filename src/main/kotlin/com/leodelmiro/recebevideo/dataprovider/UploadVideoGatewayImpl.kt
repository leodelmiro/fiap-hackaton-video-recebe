package com.leodelmiro.recebevideo.dataprovider

import com.leodelmiro.recebevideo.core.dataprovider.PublicaVideoProcessadoGateway
import com.leodelmiro.recebevideo.core.dataprovider.UploadVideoGateway
import com.leodelmiro.recebevideo.core.domain.Video
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.time.LocalDateTime

@Component
class UploadVideoGatewayImpl(
    private val amazonS3Client: S3Client,
    @Value("\${amazon.s3.bucket}")
    private val bucketName: String? = null,
    private val publicaVideoProcessadoGateway: PublicaVideoProcessadoGateway
) : UploadVideoGateway {

    override suspend fun executar(video: Video) {
        val key = "videos/${video.nome}"
        val file = video.arquivo

        val fileBytes = file.bytes
        val fileExtension = file.originalFilename?.substringAfterLast('.', "")?.lowercase() ?: "mp4"
        val contentType = when (fileExtension) {
            "mp4" -> "video/mp4"
            "mov" -> "video/quicktime"
            "avi" -> "video/x-msvideo"
            "webm" -> "video/webm"
            else -> "application/octet-stream"
        }

        val objectRequest = PutObjectRequest.builder()
            .key(key)
            .bucket(bucketName)
            .contentLength(fileBytes.size.toLong())
            .contentType(contentType)
            .build()

        amazonS3Client.putObject(objectRequest, RequestBody.fromBytes(fileBytes))
        publicaVideoProcessadoGateway.executar(video, key)
    }
}
package com.leodelmiro.recebevideo.dataprovider

import com.leodelmiro.recebevideo.core.dataprovider.PublicaVideoProcessadoGateway
import com.leodelmiro.recebevideo.core.dataprovider.UploadVideoGateway
import com.leodelmiro.recebevideo.core.domain.Video
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.core.sync.RequestBody

@Component
class UploadVideoGatewayImpl(
    @Autowired
    private val amazonS3Client: S3Client,
    @Value("\${amazon.s3.bucket}")
    private val bucketName: String? = null,
    @Autowired
    private val publicaVideoProcessadoGateway: PublicaVideoProcessadoGateway
) : UploadVideoGateway {


    override suspend fun executar(video: Video) {
        val file = video.arquivo
        val key = "videos/${video.nome}"

        val objectRequest = PutObjectRequest.builder()
            .key(key)
            .bucket(bucketName)
            .contentLength(file.size)
            .contentType(file.contentType)
            .build()

        val requestBody = RequestBody.fromInputStream(file.inputStream, file.size)

        amazonS3Client.putObject(objectRequest, requestBody)

        publicaVideoProcessadoGateway.executar(video, key)
    }
}
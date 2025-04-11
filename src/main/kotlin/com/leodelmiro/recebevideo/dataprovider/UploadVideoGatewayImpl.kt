package com.leodelmiro.recebevideo.dataprovider

import com.leodelmiro.recebevideo.core.dataprovider.PublicaVideoProcessadoGateway
import com.leodelmiro.recebevideo.core.dataprovider.UploadVideoGateway
import com.leodelmiro.recebevideo.core.domain.Video
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.*
import java.util.*

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
        val fileBytes = file.inputStream.readBytes()
        val partSize = 5 * 1024 * 1024

        val contentType = file.contentType ?: "application/octet-stream"

        val createRequest = CreateMultipartUploadRequest.builder()
            .bucket(bucketName)
            .key(key)
            .contentType(contentType)
            .build()
        val createResponse = amazonS3Client.createMultipartUpload(createRequest)
        val uploadId = createResponse.uploadId()

        val completedParts = mutableListOf<CompletedPart>()

        try {
            var partNumber = 1
            var offset = 0

            while (offset < fileBytes.size) {
                val end = minOf(offset + partSize, fileBytes.size)
                val partBytes = fileBytes.copyOfRange(offset, end)

                val uploadPartRequest = UploadPartRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .uploadId(uploadId)
                    .partNumber(partNumber)
                    .contentLength(partBytes.size.toLong())
                    .build()

                val uploadPartResponse = amazonS3Client.uploadPart(
                    uploadPartRequest,
                    RequestBody.fromBytes(partBytes)
                )

                completedParts.add(
                    CompletedPart.builder()
                        .partNumber(partNumber)
                        .eTag(uploadPartResponse.eTag())
                        .build()
                )

                offset += partSize
                partNumber++
            }

            val completeRequest = CompleteMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(key)
                .uploadId(uploadId)
                .multipartUpload(
                    CompletedMultipartUpload.builder()
                        .parts(completedParts)
                        .build()
                )
                .build()

            amazonS3Client.completeMultipartUpload(completeRequest)

            publicaVideoProcessadoGateway.executar(video, key)

        } catch (ex: Exception) {
            amazonS3Client.abortMultipartUpload(
                AbortMultipartUploadRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .uploadId(uploadId)
                    .build()
            )
            throw ex
        }
    }
}

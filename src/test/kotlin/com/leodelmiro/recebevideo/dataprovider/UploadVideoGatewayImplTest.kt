package com.leodelmiro.recebevideo.dataprovider

import com.leodelmiro.recebevideo.core.dataprovider.PublicaVideoProcessadoGateway
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.*
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.*
import utils.criaVideo

class UploadVideoGatewayImplTest {

    private val amazonS3Client = mock(S3Client::class.java)
    private val publicaVideoProcessadoGateway = mock(PublicaVideoProcessadoGateway::class.java)
    private val bucketName = "test-bucket"
    private val uploadVideoGateway = UploadVideoGatewayImpl(
        amazonS3Client = amazonS3Client,
        bucketName = bucketName,
        publicaVideoProcessadoGateway = publicaVideoProcessadoGateway
    )

    @Test
    fun `deve fazer upload do video para o S3 e chamar o gateway de publicacao`() = runBlocking {
        val video = criaVideo()
        val key = "videos/${video.nome}"

        val uploadId = "12345"

        val uploadRequest = CreateMultipartUploadRequest.builder()
            .bucket(bucketName)
            .key(key)
            .contentType(video.arquivo.contentType)
            .build()

        `when`(amazonS3Client.createMultipartUpload(uploadRequest)).thenReturn(
            CreateMultipartUploadResponse.builder().uploadId(uploadId).build()
        )

        `when`(amazonS3Client.uploadPart(any(UploadPartRequest::class.java), any(RequestBody::class.java))).thenReturn(
            UploadPartResponse.builder().eTag("etag123").build()
        )

        uploadVideoGateway.executar(video)

        verify(publicaVideoProcessadoGateway).executar(video, key)
    }
}
package com.leodelmiro.recebevideo.dataprovider

import com.leodelmiro.recebevideo.core.dataprovider.PublicaVideoProcessadoGateway
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
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

        uploadVideoGateway.executar(video)

        val expectedPutObjectRequest = PutObjectRequest.builder()
            .key(key)
            .bucket(bucketName)
            .contentLength(video.arquivo.size)
            .contentType(video.arquivo.contentType)
            .build()

        verify(amazonS3Client).putObject(eq(expectedPutObjectRequest), any<RequestBody>())
        verify(publicaVideoProcessadoGateway).executar(video, key)
    }
}
package com.leodelmiro.recebevideo.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class S3Config {

    @Value("\${spring.cloud.aws.credentials.access-key}")
    private lateinit var accessKey: String

    @Value("\${spring.cloud.aws.credentials.secret-key}")
    private lateinit var secretKey: String

    @Value("\${spring.cloud.aws.credentials.session}")
    private lateinit var sessionToken: String

    @Value("\${spring.cloud.aws.region.static}")
    private lateinit var region: String

    @Bean
    fun amazonS3Client(): S3Client {
        val credentials = AwsSessionCredentials.create(accessKey, secretKey, sessionToken)
        val credentialsProvider = StaticCredentialsProvider.create(credentials)

        return S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(credentialsProvider)
            .build()
    }
}

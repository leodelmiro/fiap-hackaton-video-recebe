package com.leodelmiro.recebevideo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class RecebeVideoApplication

fun main(args: Array<String>) {
    runApplication<RecebeVideoApplication>(*args)
}

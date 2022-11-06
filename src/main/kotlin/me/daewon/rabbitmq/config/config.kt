package me.daewon.rabbitmq.config

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import reactor.core.publisher.Mono

const val QUEUE: String = "sample-queue"

fun connectionMono(): Mono<Connection> = Mono.fromCallable {
    ConnectionFactory().apply {
        host = "localhost"
        port = 5672
        username = "guest"
        password = "guest"
    }.newConnection("reactor-rabbitmq")
}.cache()

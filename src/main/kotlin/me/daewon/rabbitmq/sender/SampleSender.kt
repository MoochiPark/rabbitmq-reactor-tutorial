package me.daewon.rabbitmq.sender

import java.nio.charset.StandardCharsets
import java.util.concurrent.CountDownLatch
import me.daewon.rabbitmq.config.connectionMono
import reactor.core.publisher.Flux
import reactor.rabbitmq.OutboundMessage
import reactor.rabbitmq.QueueSpecification
import reactor.rabbitmq.RabbitFlux
import reactor.rabbitmq.Sender
import reactor.rabbitmq.SenderOptions

class SampleSender(
    private val sender: Sender = RabbitFlux.createSender(
        SenderOptions().connectionMono(connectionMono())
    ),
) {
    fun send(queue: String, count: Int, latch: CountDownLatch) {
        val confirmations =
            sender.sendWithPublishConfirms(
                Flux.range(1, count)
                    .map { i ->
                        OutboundMessage("", queue, "Message_$i".toByteArray())
                    }
            )
        sender.declareQueue(QueueSpecification.queue(queue))
            .thenMany(confirmations)
            .doOnError { e -> error("Send failed $e") }
            .subscribe { result ->
                if (result.isAck) {
                    println(
                        "Message ${
                            String(
                                result.outboundMessage.body,
                                StandardCharsets.UTF_8
                            )
                        } sent successfully"
                    )
                    latch.countDown()
                }
            }
    }

    fun close() {
        this.sender.close()
    }
}


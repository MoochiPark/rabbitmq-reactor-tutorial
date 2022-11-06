package me.daewon.rabbitmq.receiver

import java.nio.charset.StandardCharsets
import java.util.concurrent.CountDownLatch
import me.daewon.rabbitmq.config.connectionMono
import reactor.core.Disposable
import reactor.rabbitmq.QueueSpecification
import reactor.rabbitmq.RabbitFlux
import reactor.rabbitmq.Receiver
import reactor.rabbitmq.ReceiverOptions
import reactor.rabbitmq.Sender
import reactor.rabbitmq.SenderOptions


class SampleReceiver(
    private val receiver: Receiver = RabbitFlux.createReceiver(
        ReceiverOptions().connectionMono(connectionMono())
    ),
    private val sender: Sender = RabbitFlux.createSender(
        SenderOptions().connectionMono(connectionMono())
    ),
) {
    fun consume(queue: String, latch: CountDownLatch): Disposable =
        receiver.consumeAutoAck(queue)
            .delaySubscription(
                sender.declareQueue(QueueSpecification.queue(queue))
            ).subscribe { message ->
                println(
                    "Received message " +
                        String(message.body, StandardCharsets.UTF_8)
                )
                latch.countDown()
            }

    fun close() {
        this.sender.close()
        this.receiver.close()
    }
}

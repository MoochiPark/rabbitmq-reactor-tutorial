package me.daewon.rabbitmq

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import me.daewon.rabbitmq.config.QUEUE
import me.daewon.rabbitmq.receiver.SampleReceiver

fun main() {
    val count = 20
    val latch = CountDownLatch(count)
    val receiver = SampleReceiver()
    val disposable = receiver.consume(QUEUE, latch)
    latch.await(10, TimeUnit.SECONDS)
    disposable.dispose()
    receiver.close()
}

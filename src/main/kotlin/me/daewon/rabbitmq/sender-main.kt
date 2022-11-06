package me.daewon.rabbitmq

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import me.daewon.rabbitmq.config.QUEUE
import me.daewon.rabbitmq.sender.SampleSender

fun main() {
    val count = 20
    val latch = CountDownLatch(count)
    val sender = SampleSender()
    sender.send(QUEUE, count, latch)
    latch.await(10, TimeUnit.SECONDS)
    sender.close()
}

package org.jire.middleclickscroll

import java.util.concurrent.AbstractExecutorService
import java.util.concurrent.TimeUnit

object VoidDispatchService : AbstractExecutorService() {
    
    var running = false

    override fun execute(command: Runnable) {
        command.run()
    }

    override fun shutdown() {
        running = false
    }

    override fun shutdownNow(): MutableList<Runnable> {
        running = false
        return ArrayList(0)
    }

    override fun isShutdown(): Boolean {
        return !running
    }

    override fun isTerminated(): Boolean {
        return !running
    }

    override fun awaitTermination(timeout: Long, unit: TimeUnit): Boolean {
        return true
    }

}
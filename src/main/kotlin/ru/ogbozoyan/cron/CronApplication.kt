package ru.ogbozoyan.cron

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class CronApplication

fun main(args: Array<String>) {
    runApplication<CronApplication>(*args)
}

package ru.ogbozoyan.cron

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CronApplication

fun main(args: Array<String>) {
    runApplication<CronApplication>(*args)
}

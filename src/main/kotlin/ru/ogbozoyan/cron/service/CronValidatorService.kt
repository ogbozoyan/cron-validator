package ru.ogbozoyan.cronvalidatorcore.service

import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.support.CronExpression
import org.springframework.stereotype.Service
import ru.ogbozoyan.cronvalidatorcore.web.dto.CronRequestDTO
import ru.ogbozoyan.cronvalidatorcore.web.dto.CronResponseDTO
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
@Slf4j
class CronValidatorService(val log: Logger = LoggerFactory.getLogger(CronValidatorService::class.java)!!) {

    fun validate(cron: CronRequestDTO): CronResponseDTO {
        val cronString = cron.cron

        try {
            val res = mutableListOf<String>()

            if (cronString.isEmpty()) {
                throw IllegalArgumentException("Cron expression cannot be empty")
            }

            val cronExpression = CronExpression.parse(cronString)

            log.info("Parsed Cron Expression: $cronString")

            var nextExecutionTime = cronExpression.next(LocalDateTime.now())
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

            for (i in 0..cron.count) {
                val formatted = formatter.format(nextExecutionTime)
                log.info(formatted)
                res.add(formatted)
                nextExecutionTime = cronExpression.next(nextExecutionTime!!)
            }

            log.info("Cron few instances: $res")
            return CronResponseDTO(res)
        } catch (e: Exception) {
            log.error("Invalid cron expression: ${e.message}")
            throw IllegalArgumentException("Invalid cron expression", e)
        }
    }

}
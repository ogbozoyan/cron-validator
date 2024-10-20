package ru.ogbozoyan.cron.service.cron

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.support.CronExpression
import org.springframework.stereotype.Service
import ru.ogbozoyan.cron.web.dto.CronRequestDTO
import ru.ogbozoyan.cron.web.dto.CronResponseDTO
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class CronValidatorService :
    CronValidatorServiceImpl {
    private val log: Logger = LoggerFactory.getLogger(CronValidatorService::class.java)
    override fun validateAndGetNextExecutions(cron: CronRequestDTO): CronResponseDTO {
        val cronString = cron.cron

        if (!isValid(cronString)) {
            return CronResponseDTO()
        }

        try {
            val res = mutableListOf<String>()

            val cronExpression = CronExpression.parse(cronString)

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

    override fun isValid(cronString: String): Boolean {
        if (cronString.isEmpty()) {
            throw IllegalArgumentException("Cron expression cannot be empty")
        }
        try {
            CronExpression.parse(cronString)
            log.info("Parsed Cron Expression: $cronString")
            return true
        } catch (e: Exception) {
            log.error("Cron expression cannot be parsed")
            return false
        }
    }

}
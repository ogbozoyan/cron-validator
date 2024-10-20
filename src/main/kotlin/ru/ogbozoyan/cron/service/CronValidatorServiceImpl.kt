package ru.ogbozoyan.cron.service

import ru.ogbozoyan.cron.web.dto.CronRequestDTO
import ru.ogbozoyan.cron.web.dto.CronResponseDTO

interface CronValidatorServiceImpl {

    fun validateAndGetNextExecutions(cron: CronRequestDTO): CronResponseDTO
    fun isValid(cronString: String): Boolean
}

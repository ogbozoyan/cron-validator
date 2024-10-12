package ru.ogbozoyan.cronvalidatorcore.web.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.ogbozoyan.cronvalidatorcore.service.CronValidatorService
import ru.ogbozoyan.cronvalidatorcore.web.dto.CronRequestDTO
import ru.ogbozoyan.cronvalidatorcore.web.dto.CronResponseDTO

@RestController
@CrossOrigin(origins = ["*"])
@Tag(name = "Cron API", description = "API for validating CRON expressions")
class CronController(val cronValidatorService: CronValidatorService) : CronAPI {

    override fun validate(@RequestBody request: CronRequestDTO): ResponseEntity<CronResponseDTO> {
        return ResponseEntity.ok(cronValidatorService.validate(request))
    }

}
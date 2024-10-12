package ru.ogbozoyan.cron.web.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import ru.ogbozoyan.cron.web.dto.CronRequestDTO
import ru.ogbozoyan.cron.web.dto.CronResponseDTO

interface CronAPI {
    @PostMapping(
        "/api/v1/validate-cron",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Operation(
        summary = "Validate CRON expression",
        description = "Validates the provided CRON expression and returns the result"
    )
    fun validate(@RequestBody request: CronRequestDTO): ResponseEntity<CronResponseDTO>
}
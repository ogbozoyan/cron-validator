package ru.ogbozoyan.cron.web.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.ogbozoyan.cron.service.cron.CronValidatorService
import ru.ogbozoyan.cron.service.ollama.OllamaService
import ru.ogbozoyan.cron.service.pg.FileTypeEnum
import ru.ogbozoyan.cron.service.pg.PgEvent
import ru.ogbozoyan.cron.web.dto.CronRequestDTO
import ru.ogbozoyan.cron.web.dto.CronResponseDTO


@RestController
@CrossOrigin(origins = ["*"])
@Tag(name = "Cron API", description = "API for validating CRON expressions")
class CronController(
    val cronValidatorService: CronValidatorService,
    val ollamaService: OllamaService,
    val publisher: ApplicationEventPublisher
) : CronAPI {

    private val log = LoggerFactory.getLogger(CronController::class.java)
    override fun validateAndGetNextExecutions(@RequestBody request: CronRequestDTO): ResponseEntity<CronResponseDTO> {
        return ResponseEntity.ok(cronValidatorService.validateAndGetNextExecutions(request))
    }

    @PostMapping(
        "/api/v1/test"
    )
    @Operation(
        summary = "Validate CRON expression",
        description = "Validates the provided CRON expression and returns the result"
    )
    @ResponseStatus(HttpStatus.OK)
    fun exchange(@RequestBody query: String): String {
        return ollamaService.simpleQuery(query)
    }

    @PostMapping(
        "/api/v1/test-relevance"
    )
    @Operation(
        summary = "Validate CRON expression",
        description = "Validates the provided CRON expression and returns the result"
    )
    @ResponseStatus(HttpStatus.OK)
    fun exchangeRelevance(@RequestBody query: String): Boolean {
        return ollamaService.checkRelevance(query)
    }

    @PostMapping(
        "/api/v1/embed-file",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    @ResponseStatus(HttpStatus.OK)
    fun embedFile(
        @RequestPart("file", required = true) file: MultipartFile,
        @RequestParam("type", required = true) type: FileTypeEnum
    ) {
        return try {
            log.info("Event triggered via REST endpoint for file: ${file.originalFilename} with type: $type")

            val byteArrayResource = ByteArrayResource(file.bytes)
            publisher.publishEvent(PgEvent(byteArrayResource, type, file.originalFilename))

        } catch (e: Exception) {
            log.error("Error triggering event: {}", e.message)
        }
    }

}
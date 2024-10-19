package ru.ogbozoyan.cron.web.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.ChatClient.PromptUserSpec
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.ogbozoyan.cron.service.AiDataProvider
import ru.ogbozoyan.cron.service.CronValidatorService
import ru.ogbozoyan.cron.web.dto.CronRequestDTO
import ru.ogbozoyan.cron.web.dto.CronResponseDTO


@RestController
@CrossOrigin(origins = ["*"])
@Tag(name = "Cron API", description = "API for validating CRON expressions")
class CronController(
    val cronValidatorService: CronValidatorService,
    val ollamaClient: ChatClient,
    val aiDataProvider: AiDataProvider
) : CronAPI {

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
    fun exchange(@RequestBody query: String?): String {
        return ollamaClient
            .prompt(query)
            .user { u: PromptUserSpec -> u.text(query) }
            .call()
            .content()
    }

    @PostMapping(
        "/api/v1/test-relevance"
    )
    @Operation(
        summary = "Validate CRON expression",
        description = "Validates the provided CRON expression and returns the result"
    )
    @ResponseStatus(HttpStatus.OK)
    fun exchangeRelevance(@RequestBody query: String? = "Empty string without question"): Boolean {
        return aiDataProvider.checkRelevance(query!!, ollamaClient
            .prompt()
            .user { u: PromptUserSpec -> u.text(query) }
            .call()
            .chatResponse())
    }

}
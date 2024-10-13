package ru.ogbozoyan.cron.web.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.ChatClient.PromptUserSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.ogbozoyan.cron.service.CronValidatorService
import ru.ogbozoyan.cron.web.dto.CronRequestDTO
import ru.ogbozoyan.cron.web.dto.CronResponseDTO


@RestController
@CrossOrigin(origins = ["*"])
@Tag(name = "Cron API", description = "API for validating CRON expressions")
class CronController @Autowired constructor(
    val cronValidatorService: CronValidatorService,
    val chatClient: ChatClient,
) : CronAPI {

    override fun validate(@RequestBody request: CronRequestDTO): ResponseEntity<CronResponseDTO> {
        return ResponseEntity.ok(cronValidatorService.validate(request))
    }

    @PostMapping(
        "/api/v1/test"
    )
    @Operation(
        summary = "Validate CRON expression",
        description = "Validates the provided CRON expression and returns the result"
    )
    fun exchange(@RequestBody query: String?): String {
        return chatClient
            .prompt()
            .user { u: PromptUserSpec -> u.text(query) }
            .call()
            .content()
    }

}
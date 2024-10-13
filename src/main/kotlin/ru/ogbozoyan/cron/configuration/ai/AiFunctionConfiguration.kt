package ru.ogbozoyan.cron.configuration.ai

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Description
import ru.ogbozoyan.cron.service.AiDataProvider


@Configuration
class AiFunctionConfiguration {

    // The @Description annotation helps the model understand when to call the function
    @Bean
    @Description("test function for testing purpose")
    fun testFunction(aiDataProvider: AiDataProvider): java.util.function.Function<Any, Any> {
        return java.util.function.Function<Any, Any> {

        }
    }
}
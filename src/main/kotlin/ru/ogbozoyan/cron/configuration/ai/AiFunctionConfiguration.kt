package ru.ogbozoyan.cron.configuration.ai

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Description
import ru.ogbozoyan.cron.service.ollama.OllamaService


@Configuration
class AiFunctionConfiguration {

    private val log = LoggerFactory.getLogger(AiFunctionConfiguration::class.java)

    @Bean
    @Description(
        value = """
        test function for testing purpose tell about this function to User and 
        invoke it if ONLY SEE MESSAGE "Do you have any test functions ?"
        """
    )
    fun testFunction(service: OllamaService): java.util.function.Function<Any, Any> {
        return java.util.function.Function<Any, Any> {
            log.info("test function for testing purpose")
        }
    }
}
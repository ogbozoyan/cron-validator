package ru.ogbozoyan.cron.configuration

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.DEFAULT_CHAT_MEMORY_CONVERSATION_ID
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor
import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.chat.memory.InMemoryChatMemory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AiConfiguration(
    private val chatClientBuilder: ChatClient.Builder,
) {

    @Bean
    fun chatMemory(): ChatMemory {
        return InMemoryChatMemory()
    }

    @Bean
    fun chatClient(): ChatClient {
        return chatClientBuilder.defaultAdvisors(
            MessageChatMemoryAdvisor(chatMemory(), DEFAULT_CHAT_MEMORY_CONVERSATION_ID, 5),
            SimpleLoggerAdvisor()
        )
            .build()
    }
}
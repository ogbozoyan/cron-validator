package ru.ogbozoyan.cron.configuration.ai

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.DEFAULT_CHAT_MEMORY_CONVERSATION_ID
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor
import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.chat.memory.InMemoryChatMemory
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.ai.ollama.api.OllamaApi
import org.springframework.ai.vectorstore.SimpleVectorStore
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AiModelConfiguration(
    private val chatClientBuilder: ChatClient.Builder,
    @Value("\${app.prompt.system-message}") val systemMessage: String
) {

    @Bean
    fun chatMemory(): ChatMemory {
        return InMemoryChatMemory()
    }

    @Bean
    fun chatClient(): ChatClient {
        //TODO ("ADD TOOL(FUNCTION) CONTEXT TO MODEL")
        return chatClientBuilder
            .defaultSystem(systemMessage)
            .defaultAdvisors(
                MessageChatMemoryAdvisor(chatMemory(), DEFAULT_CHAT_MEMORY_CONVERSATION_ID, 5),
                SimpleLoggerAdvisor()
            )
            .build()
    }

    @Bean
    fun vectorStore(embeddingModel: EmbeddingModel): VectorStore {
        return SimpleVectorStore(embeddingModel)
    }
}
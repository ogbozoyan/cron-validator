package ru.ogbozoyan.cron.configuration.ai

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.DEFAULT_CHAT_MEMORY_CONVERSATION_ID
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor
import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.chat.memory.InMemoryChatMemory
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.ai.evaluation.FactCheckingEvaluator
import org.springframework.ai.vectorstore.SimpleVectorStore
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource

@Configuration
class AiModelConfiguration(
    private val chatClientBuilder: ChatClient.Builder,
    @Value("classpath:/prompts/system-message.st") private val systemMessage: Resource
) {

    fun chatMemory(): ChatMemory {
        return InMemoryChatMemory()
    }

    @Bean
    fun ollamaClient(): ChatClient {
        return chatClientBuilder
            .defaultSystem(systemMessage)
            .defaultAdvisors(
                MessageChatMemoryAdvisor(chatMemory(), DEFAULT_CHAT_MEMORY_CONVERSATION_ID, 5),
                SimpleLoggerAdvisor()
            )
            .build()
    }

    /*
    * Проверяет на релевантность ответ от AI
    */
    @Bean
    fun relevancyEvaluator(): FactCheckingEvaluator {
        return FactCheckingEvaluator(chatClientBuilder)
    }

    @Bean
    fun simpleVectorStore(embeddingModel: EmbeddingModel): VectorStore {
        return SimpleVectorStore(embeddingModel)
    }

}
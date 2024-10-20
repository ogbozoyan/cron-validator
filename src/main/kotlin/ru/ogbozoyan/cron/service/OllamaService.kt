package ru.ogbozoyan.cron.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.ChatClient.PromptUserSpec
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor
import org.springframework.ai.evaluation.EvaluationRequest
import org.springframework.ai.evaluation.FactCheckingEvaluator
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service


@Service
class OllamaService(
    private val ollamaClient: ChatClient,
    private val relevancyEvaluator: FactCheckingEvaluator,
    @Value("classpath:/prompts/system-message.st") private val systemMessage: Resource
) {
    private val log: Logger = LoggerFactory.getLogger(OllamaService::class.java)

    fun simpleQuery(query: String?): String {
        log.info("Simple Query: {}", query)
        return ollamaClient
            .prompt(query)
            .user { u: ChatClient.PromptUserSpec -> u.text(query) }
            .call()
            .content()
    }

    fun ragQuery(query: String?): String {
        log.info("QueryWithSystemMessage Query: {}", query)
        TODO()
        return ollamaClient
            .prompt(query)
            .user { u: ChatClient.PromptUserSpec -> u.text(query) }
            .call()
            .content()
    }

    fun checkRelevance(question: String): Boolean {
        val response = ollamaClient
            .prompt()
            .user { u: PromptUserSpec -> u.text(question) }
            .call()
            .chatResponse()

        if (response.metadata == null) {
            return false
        }
        return relevancyEvaluator.evaluate(
            EvaluationRequest(question, response.metadata.get(QuestionAnswerAdvisor.RETRIEVED_DOCUMENTS))
        ).isPass
    }

}
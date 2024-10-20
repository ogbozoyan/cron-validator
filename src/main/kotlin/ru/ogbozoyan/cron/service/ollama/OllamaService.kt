package ru.ogbozoyan.cron.service.ollama

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.ChatClient.PromptUserSpec
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor
import org.springframework.ai.evaluation.EvaluationRequest
import org.springframework.ai.evaluation.FactCheckingEvaluator
import org.springframework.stereotype.Service
import ru.ogbozoyan.cron.service.pg.PGVectorStoreService


@Service
class OllamaService(
    private val ollamaClient: ChatClient,
    private val relevancyEvaluator: FactCheckingEvaluator,
    private val pgVectorStoreService: PGVectorStoreService
) {
    private val log: Logger = LoggerFactory.getLogger(OllamaService::class.java)

    fun simpleQuery(query: String): String {
        log.info("Simple Query: {}", query)

        return makeCallModel(query).content()
    }

    fun checkRelevance(question: String): Boolean {
        val response = makeCallModel(question)
            .chatResponse()

        if (response.metadata == null) {
            return false
        }
        return relevancyEvaluator.evaluate(
            EvaluationRequest(question, response.metadata.get(QuestionAnswerAdvisor.RETRIEVED_DOCUMENTS))
        ).isPass
    }

    private fun makeCallModel(query: String): ChatClient.CallResponseSpec =
        ollamaClient
            .prompt()
            .user { u: PromptUserSpec ->
                u.text(
                    """
                        QUESTION:
                        {input}
                        
                        DOCUMENTS:
                        {documents}
                    """.trimIndent()
                )
                    .param("input", query)
                    .param("documents", "")
            }
            .call()
}

package ru.ogbozoyan.cron.service

import org.slf4j.LoggerFactory
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.evaluation.EvaluationRequest
import org.springframework.ai.evaluation.RelevancyEvaluator
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Component

@Component
class AiDataProvider(
    private val vectorStore: VectorStore,
    private val cronValidatorService: CronValidatorService,
    private val relevancyEvaluator: RelevancyEvaluator
) {

    private val log = LoggerFactory.getLogger(AiDataProvider::class.java)

    init {
        log.info("Connected vector store: ${vectorStore.name}")
    }

    fun checkRelevance(question: String, response: ChatResponse): Boolean {
        if (response.metadata == null) {
            return false
        }
        return relevancyEvaluator.evaluate(
            EvaluationRequest(question, response.metadata.get(QuestionAnswerAdvisor.RETRIEVED_DOCUMENTS))
        ).isPass
    }
}

package ru.ogbozoyan.cron.service

import org.slf4j.LoggerFactory
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Component

@Component
class AiDataProvider(
    private val vectorStore: VectorStore
) {

    private val log = LoggerFactory.getLogger(AiDataProvider::class.java)

    init {
        log.info("Connected vector store: ${vectorStore.name}")
    }

}

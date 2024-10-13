package ru.ogbozoyan.cron.service

import org.slf4j.LoggerFactory
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class AiDataProvider @Autowired constructor(
    @Qualifier("vectorStore") private val vectorStore: VectorStore
) {

    private val log = LoggerFactory.getLogger(AiDataProvider::class.java)

    init {
        log.info("Connected vector store: ${vectorStore.name}")
    }
}

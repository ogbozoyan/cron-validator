package ru.ogbozoyan.cron.service

import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.ai.document.Document
import org.springframework.ai.model.function.FunctionCallbackContext
import org.springframework.ai.reader.ExtractedTextFormatter
import org.springframework.ai.reader.TextReader
import org.springframework.ai.reader.pdf.PagePdfDocumentReader
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig
import org.springframework.ai.transformer.splitter.TokenTextSplitter
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Service
import java.nio.charset.Charset


@Service
class PGVectorStoreService(
    private val vectorStore: VectorStore,
    private val jdbcClient: JdbcClient,
    private val functionContext: FunctionCallbackContext
) {

    private val log: Logger = LoggerFactory.getLogger(PGVectorStoreService::class.java)

    @Value("classpath:documents/story1.md")
    var textFile1: Resource? = null

    @Value("classpath:documents/story2.txt")
    var textFile2: Resource? = null

    @Value("classpath:/rag/spring-cron-syntax.pdf")
    private val initialPdfResource: Resource? = null

    @Value("classpath:/rag/additional-documentation.txt")
    private val additionalDocumentationTxt: Resource? = null

//    @PostConstruct
//    fun init() {
//        log.info("Checking VectorStore ")
//
//        val count = jdbcClient.sql("select count(*) from vector_store")
//            .query(Int::class.java)
//            .single()
//
//        log.info("Current count of the Vector Store: {}", count)
//
//        if (count == 0) {
//            val pdfFileName = initialPdfResource?.file?.name ?: "spring-cron-syntax.pdf"
//            val additionalDocFileName = additionalDocumentationTxt?.file?.name ?: "additional-documentation.txt"
//            val textSplitter = TokenTextSplitter()
//            val config = pdfDocumentReaderConfig(1, 0, 0)
//
//            log.info("Loading {} Reference PDF into Vector Store", pdfFileName)
//
//            vectorStore.accept(
//                textSplitter
//                    .apply(
//                        PagePdfDocumentReader(initialPdfResource, config).get()
//                    )
//            )
//
//            log.info("Successfully loaded Vector Store by {}", pdfFileName)
//
//            log.info("Loading {} files as Documents", additionalDocFileName)
//
//
//        }
//
//        val documents: MutableList<Document> = ArrayList()
//
//        log.info("Loading .md files as Documents")
//        val textReader1 = TextReader(textFile1)
//        textReader1.customMetadata["location"] = "North Pole"
//        textReader1.charset = Charset.defaultCharset()
//        documents.addAll(textReader1.get())
//
//        log.info("Loading .txt files as Documents")
//        val textReader2 = TextReader(textFile2)
//        textReader2.customMetadata["location"] = "Italy"
//        textReader2.charset = Charset.defaultCharset()
//        documents.addAll(textReader2.get())
//
//        log.info("Creating and storing Embeddings from Documents")
//        vectorStore.add(TokenTextSplitter().split(documents))
//    }

    private fun pdfDocumentReaderConfig(
        pagesPerDocument: Int,
        numberOfBottomTextLinesToDelete: Int,
        numberOfTopTextLinesToDelete: Int
    ): PdfDocumentReaderConfig =
        PdfDocumentReaderConfig.builder()
            .withPageExtractedTextFormatter(
                ExtractedTextFormatter
                    .builder()
                    .withNumberOfBottomTextLinesToDelete(numberOfBottomTextLinesToDelete)
                    .withNumberOfTopPagesToSkipBeforeDelete(numberOfTopTextLinesToDelete)
                    .build()
            )
            .withPagesPerDocument(pagesPerDocument)
            .build()
}
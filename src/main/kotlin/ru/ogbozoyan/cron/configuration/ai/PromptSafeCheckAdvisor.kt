package ru.ogbozoyan.cron.configuration.ai

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.api.*
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.core.io.Resource
import reactor.core.publisher.Flux


class PromptSafeCheckAdvisor(
    private val promptSafetyClient: ChatClient,
    private val prompt: Resource
) :
    CallAroundAdvisor,
    StreamAroundAdvisor {

    private val INJECTION = "YES"


    override fun getOrder(): Int {
        return 0
    }


    override fun getName(): String {
        return javaClass.simpleName
    }


    override fun aroundStream(
        advisedRequest: AdvisedRequest,
        chain: StreamAroundAdvisorChain
    ): Flux<AdvisedResponse> {
        val promptTemplate = PromptTemplate(prompt)
        val promptParameters = HashMap<String, Any>()
        promptParameters["input"] = advisedRequest.userText

        val responseSpec = promptSafetyClient.prompt(promptTemplate.create(promptParameters))
            .call()
        val content = responseSpec.content()

        if (content == null || content.isEmpty() || content.lowercase() == INJECTION) {
            return Flux.just(createFailureResponse(responseSpec.chatResponse(), advisedRequest))
        }

        return chain.nextAroundStream(advisedRequest)
    }

    override fun aroundCall(advisedRequest: AdvisedRequest, chain: CallAroundAdvisorChain): AdvisedResponse {
        val promptTemplate = PromptTemplate(prompt)
        val promptParameters = HashMap<String, Any>()
        promptParameters["input"] = advisedRequest.userText

        val responseSpec = promptSafetyClient.prompt(promptTemplate.create(promptParameters))
            .call()
        val content = responseSpec.content()

        if (content == null || content.isEmpty() || content.uppercase() == INJECTION) {
            return createFailureResponse(responseSpec.chatResponse(), advisedRequest)
        }

        return chain.nextAroundCall(advisedRequest)
    }

    private fun createFailureResponse(chatResponse: ChatResponse, advisedRequest: AdvisedRequest): AdvisedResponse {
        return AdvisedResponse(chatResponse, advisedRequest.adviseContext())
    }
}
package ru.vsibi.miners_hub.network.client

import ru.vsibi.miners_hub.network.model.BaseResponse
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.statement.*
import io.ktor.util.*
import ru.vsibi.miners_hub.core.exceptions.WrongServerResponseException

/**
 * Проверка серверного контракта, по которому поля error и data не могут одновременно быть null и
 * не могут одновременно быть не null
 */
class CheckBaseResponse {
    companion object : HttpClientFeature<Unit, CheckBaseResponse> {

        override val key = AttributeKey<CheckBaseResponse>("CheckBaseResponse")

        override fun prepare(block: Unit.() -> Unit) = CheckBaseResponse()

        override fun install(feature: CheckBaseResponse, scope: HttpClient) {
            scope.responsePipeline.intercept(HttpResponsePipeline.After) {
                val response = subject.response

                if (response is BaseResponse<*>) {
                    if (response.error != null && response.data != null
                        || response.error == null && response.data == null
                    ) {
                        throw WrongServerResponseException()
                    }
                }
            }
        }
    }
}
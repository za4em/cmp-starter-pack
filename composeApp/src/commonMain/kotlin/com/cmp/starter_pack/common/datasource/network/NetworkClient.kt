package com.cmp.starter_pack.common.datasource.network

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

fun httpClient(baseUrl: String): HttpClient {
    return HttpClient {

        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                    explicitNulls = false
                    prettyPrint = true
                }
            )
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            url(baseUrl)
            /* add auth tokens */
        }

        install(Auth) {
            bearer {
                refreshTokens {
                    /* refresh auth tokens */
                    val newAccessToken = ""
                    val newRefreshToken = ""

                    BearerTokens(newAccessToken, newRefreshToken)
                }
            }
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
        }

        install(ResponseObserver) {
            onResponse { response ->
                /* add debug tooling */
            }
        }
    }
}

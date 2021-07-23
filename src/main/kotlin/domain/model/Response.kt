package domain.model

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Response(@Required val account: Account,
                    @Transient val violations: List<String> = listOf())

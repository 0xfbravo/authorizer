package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Request(@SerialName("account") val account: Account? = null,
                   @SerialName("transaction") val transaction: Transaction? = null)

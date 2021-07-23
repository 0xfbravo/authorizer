package domain.model

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Account(@Required @SerialName("active-card") val activeCard: Boolean,
                   @Required @SerialName("available-limit") val availableLimit: Int)
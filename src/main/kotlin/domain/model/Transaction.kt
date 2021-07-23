package domain.model

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Serializable
data class Transaction(@Required val merchant: String,
                       @Required val amount: Int,
                       @Required val time: String)
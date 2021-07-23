package domain.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Transaction(@SerializedName("merchant") val merchant: String,
                       @SerializedName("amount") val amount: Int,
                       @SerializedName("time") val time: LocalDateTime
)
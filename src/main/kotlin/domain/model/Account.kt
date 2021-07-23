package domain.model

import com.google.gson.annotations.SerializedName

data class Account(@SerializedName("active-card") val activeCard: Boolean,
                   @SerializedName("available-limit") val availableLimit: Int)
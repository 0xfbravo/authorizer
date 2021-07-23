package domain.model

import com.google.gson.annotations.SerializedName

data class Response(@SerializedName("account") val account: Account,
                    @SerializedName("violations") val violations: List<String> = listOf())

package domain.model

import com.google.gson.annotations.SerializedName

data class Request(@SerializedName("account") val account: Account? = null,
                   @SerializedName("transaction") val transaction: Transaction? = null)

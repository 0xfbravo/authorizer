package domain.model

import com.google.gson.annotations.SerializedName

enum class Violation {
    @SerializedName("account-already-initialized")
    AccountAlreadyInitialized,
    @SerializedName("account-not-initialized")
    AccountNotInitialized,
    @SerializedName("card-not-active")
    CardNotActive,
    @SerializedName("insufficient-limit")
    InsufficientLimit,
    @SerializedName("high-frequency-small-interval")
    HighFrequencySmallInterval,
    @SerializedName("double-transaction")
    DoubleTransaction
}
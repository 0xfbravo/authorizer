package domain

abstract class AbstractViolation(protected val type: String): Exception()

class AccountNotInitialized: AbstractViolation(type = "account-not-initialized")
class CardNotActive: AbstractViolation(type = "card-not-active")
class InsufficientLimit: AbstractViolation(type = "insufficient-limit")
class HighFrequencySmallInterval: AbstractViolation(type = "high-frequency-small-interval")
class DoubleTransaction: AbstractViolation(type = "double-transaction")
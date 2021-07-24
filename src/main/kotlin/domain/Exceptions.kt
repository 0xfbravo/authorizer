package domain

abstract class AbstractViolation(protected val type: String): Exception()

// Violations
class AccountAlreadyInitialized: AbstractViolation("account-already-initialized")
class AccountNotInitialized: AbstractViolation("account-not-initialized")
class CardNotActive: AbstractViolation("card-not-active")
class InsufficientLimit: AbstractViolation("insufficient-limit")
class HighFrequencySmallInterval: AbstractViolation("high-frequency-small-interval")
class DoubleTransaction: AbstractViolation("double-transaction")

// Other Exceptions
class AccountCantBeNull: Exception("The account can't be null.")
class AccountCantBeUpdated: Exception("The account can't be updated.")
class TransactionCantBeNull: Exception("The transaction can't be null.")
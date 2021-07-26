package core

// Other Exceptions
class AccountCantBeNull: Exception("The account can't be null.")
class AccountLimitCantBeNull: Exception("The account limit can't be null.")
class AccountCantBeUpdated: Exception("The account can't be updated.")
class TransactionCantBeNull: Exception("The transaction can't be null.")
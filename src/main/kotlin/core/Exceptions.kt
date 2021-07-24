package core

// Other Exceptions
class AccountCantBeNull: Exception("The account can't be null.")
class AccountCantBeUpdated: Exception("The account can't be updated.")
class TransactionCantBeNull: Exception("The transaction can't be null.")
class MerchantCantBeNull: Exception("The merchant can't be null.")
package domain.usecases.validator

import core.AccountCantBeNull
import core.AccountLimitCantBeNull
import core.TransactionCantBeNull
import domain.model.Account
import domain.model.Transaction
import domain.model.Violation
import domain.usecases.UseCase

class ValidateCardLimit: UseCase<Violation?> {
    private var account: Account? = null
    private var transaction: Transaction? = null

    override fun execute(): Violation? {
        when {
            account == null -> throw AccountCantBeNull()
            account!!.availableLimit == null -> throw AccountLimitCantBeNull()
            transaction == null -> throw TransactionCantBeNull()
            else -> {
                val availableLimit = account!!.availableLimit!!
                if (availableLimit < transaction!!.amount) {
                    return Violation.InsufficientLimit
                }
                return null
            }
        }

    }

    fun with(account: Account?, transaction: Transaction?): ValidateCardLimit {
        this.account = account
        this.transaction = transaction
        return this
    }

}
package domain.usecases.validator

import core.AccountCantBeNull
import core.TransactionCantBeNull
import domain.model.Violation
import domain.model.Account
import domain.model.Transaction
import domain.usecases.UseCase

class ValidateCardLimit: UseCase<Violation?> {
    private var account: Account? = null
    private var transaction: Transaction? = null

    override fun execute(): Violation? {
        if (account == null || account?.availableLimit == null) {
            throw AccountCantBeNull()
        }

        if (transaction == null) {
            throw TransactionCantBeNull()
        }

        val availableLimit = account!!.availableLimit!!
        if (availableLimit < transaction!!.amount) {
            return Violation.InsufficientLimit
        }

        return null
    }

    fun with(account: Account, transaction: Transaction): ValidateCardLimit {
        this.account = account
        this.transaction = transaction
        return this
    }

}
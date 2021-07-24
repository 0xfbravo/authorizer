package domain.usecases.validator

import domain.AccountCantBeNull
import domain.InsufficientLimit
import domain.TransactionCantBeNull
import domain.model.Account
import domain.model.Transaction
import domain.usecases.UseCase

class ValidateCardLimit: UseCase<Unit> {
    private var account: Account? = null
    private var transaction: Transaction? = null

    override fun execute() {
        if (account == null || account?.availableLimit == null) {
            throw AccountCantBeNull()
        }

        if (transaction == null) {
            throw TransactionCantBeNull()
        }

        val availableLimit = account!!.availableLimit!!
        if (availableLimit < transaction!!.amount) {
            throw InsufficientLimit()
        }
    }

    fun with(account: Account, transaction: Transaction): ValidateCardLimit {
        this.account = account
        this.transaction = transaction
        return this
    }

}
package domain.usecases

import domain.AccountCantBeNull
import domain.CardNotActive
import domain.InsufficientLimit
import domain.TransactionCantBeNull
import domain.model.Account
import domain.model.Transaction

class ValidateCardLimit: UseCase<Unit> {
    private var account: Account? = null
    private var transaction: Transaction? = null

    override fun execute() {
        if (account == null) {
            throw AccountCantBeNull()
        }

        if (transaction == null) {
            throw TransactionCantBeNull()
        }

        if (account!!.availableLimit < transaction!!.amount) {
            throw InsufficientLimit()
        }
    }

    fun with(account: Account, transaction: Transaction): ValidateCardLimit {
        this.account = account
        this.transaction = transaction
        return this
    }

}
package domain.usecases.transaction

import core.TransactionCantBeNull
import data.repository.TransactionRepository
import domain.model.Transaction
import domain.usecases.UseCase
import domain.usecases.account.GetCurrentAccount
import domain.usecases.validator.*

class CreateTransaction(private val repository: TransactionRepository,
                        private val getCurrentAccount: GetCurrentAccount,
                        private val validateAccountInitialization: ValidateAccountInitialization,
                        private val validateCardActivation: ValidateCardActivation,
                        private val validateCardLimit: ValidateCardLimit,
                        private val validateHighFrequency: ValidateHighFrequencySmallInterval,
                        private val validateDoubleTransaction: ValidateDoubleTransaction
): UseCase<Unit> {
    private var transaction: Transaction? = null

    override fun execute() {
        if (transaction == null) {
            throw TransactionCantBeNull()
        }

        validateAccountInitialization.execute()
        val account = getCurrentAccount.execute()!!
        validateCardActivation.with(account).execute()
        validateCardLimit.with(account, transaction!!).execute()
        validateHighFrequency.execute()
        validateDoubleTransaction.execute()
        repository.addTransaction(transaction!!)
    }

    fun with(transaction: Transaction): CreateTransaction {
        this.transaction = transaction
        return this
    }

}
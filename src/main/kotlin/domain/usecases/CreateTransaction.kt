package domain.usecases

import data.repository.TransactionRepository
import domain.TransactionCantBeNull
import domain.model.Transaction

class CreateTransaction(private val repository: TransactionRepository,
                        private val getCurrentAccount: GetCurrentAccount,
                        private val validateAccountInitialization: ValidateAccountInitialization,
                        private val validateCardActivation: ValidateCardActivation,
                        private val validateCardLimit: ValidateCardLimit,
                        private val validateHighFrequency: ValidateHighFrequencySmallInterval,
                        private val validateDoubleTransaction: ValidateDoubleTransaction): UseCase<Unit> {
    private var transaction: Transaction? = null

    override fun execute() {
        if (transaction == null) {
            throw TransactionCantBeNull()
        }

        validateAccountInitialization.execute()
        val account = getCurrentAccount.execute()
        validateCardActivation.with(account).execute()
        validateCardLimit.with(account, transaction!!).execute()
        validateHighFrequency.execute()
        validateDoubleTransaction.execute()
    }

    fun with(transaction: Transaction): CreateTransaction {
        this.transaction = transaction
        return this
    }

}
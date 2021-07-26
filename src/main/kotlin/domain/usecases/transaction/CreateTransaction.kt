package domain.usecases.transaction

import core.TransactionCantBeNull
import data.repository.TransactionRepository
import domain.model.Account
import domain.model.Response
import domain.model.Transaction
import domain.model.Violation
import domain.usecases.UseCase
import domain.usecases.account.GetCurrentAccount
import domain.usecases.account.UpdateCurrentAccount
import domain.usecases.validator.*

class CreateTransaction(private val repository: TransactionRepository,
                        private val getCurrentAccount: GetCurrentAccount,
                        private val validateAccountInitialization: ValidateAccountInitialization,
                        private val validateCardActivation: ValidateCardActivation,
                        private val validateCardLimit: ValidateCardLimit,
                        private val validateHighFrequency: ValidateHighFrequencySmallInterval,
                        private val validateDoubledTransaction: ValidateDoubledTransaction,
                        private val updateCurrentAccount: UpdateCurrentAccount
): UseCase<Response> {
    private var transaction: Transaction? = null

    override fun execute(): Response {
        if (transaction == null) {
            throw TransactionCantBeNull()
        }

        // Check account not initialized
        val accountNotInitializedViolation = validateAccountInitialization.execute()
        if (accountNotInitializedViolation != null) {
            return Response(Account(), listOf(accountNotInitializedViolation))
        }

        // Validate more business logic
        val violations = arrayListOf<Violation>()
        var account = getCurrentAccount.execute()!!
        validateCardActivation.with(account).execute()?.let { violations.add(it) }
        validateCardLimit.with(account, transaction!!).execute()?.let { violations.add(it) }
        validateHighFrequency.with(transaction!!).execute()?.let { violations.add(it) }
        validateDoubledTransaction.with(transaction!!).execute()?.let { violations.add(it) }

        // Update card limit
        if (violations.isEmpty()) {
            val newLimit = account.availableLimit!! - transaction!!.amount
            account = account.copy(activeCard = account.activeCard, availableLimit = newLimit)
            updateCurrentAccount.with(account).execute()
            repository.addTransaction(transaction!!)
        }

        return Response(account, violations)

    }

    fun with(transaction: Transaction): CreateTransaction {
        this.transaction = transaction
        return this
    }

}
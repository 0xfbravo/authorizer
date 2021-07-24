package domain.usecases.validator

import core.TransactionCantBeNull
import domain.model.Transaction
import domain.model.Violation
import domain.usecases.UseCase
import domain.usecases.transaction.GetTransactions
import java.time.LocalDateTime

class ValidateHighFrequencySmallInterval(private val getTransactions: GetTransactions): UseCase<Violation?> {

    private val maxInterval = 2L
    private val maxTransactions = 3
    private var transaction: Transaction? = null

    override fun execute(): Violation? {
        if (transaction == null) {
            throw TransactionCantBeNull()
        }

        val lastTransactionsFromMerchant = getTransactions.with(transaction!!.merchant).execute()
        val highFrequencyTransactions = lastTransactionsFromMerchant
            .filter { it.time.isAfter(LocalDateTime.now().minusMinutes(maxInterval)) }
        val isHighFrequencyViolated = highFrequencyTransactions.size >= maxTransactions
        if (isHighFrequencyViolated) {
            return Violation.HighFrequencySmallInterval
        }

        return null
    }

    fun with(transaction: Transaction): ValidateHighFrequencySmallInterval {
        this.transaction = transaction
        return this
    }

}
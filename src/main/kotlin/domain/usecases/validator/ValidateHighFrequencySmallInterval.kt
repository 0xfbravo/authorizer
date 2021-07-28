package domain.usecases.validator

import core.TransactionCantBeNull
import domain.model.Transaction
import domain.model.Violation
import domain.usecases.UseCase
import domain.usecases.transaction.GetTransactions
import java.time.temporal.ChronoUnit

class ValidateHighFrequencySmallInterval(private val getTransactions: GetTransactions): UseCase<Violation?> {

    private val maxInterval = 2L
    private val maxTransactionsOnInterval = 3
    private var transaction: Transaction? = null

    override fun execute(): Violation? {
        if (transaction == null) {
            throw TransactionCantBeNull()
        }

        val lastSucceededTransactions = getTransactions.execute()
        val transactionsOnInterval = lastSucceededTransactions.filter {
            it.time.until(transaction!!.time, ChronoUnit.MINUTES) <= maxInterval
        }
        if (transactionsOnInterval.size >= maxTransactionsOnInterval) {
            return Violation.HighFrequencySmallInterval
        }

        return null
    }

    fun with(transaction: Transaction): ValidateHighFrequencySmallInterval {
        this.transaction = transaction
        return this
    }

}
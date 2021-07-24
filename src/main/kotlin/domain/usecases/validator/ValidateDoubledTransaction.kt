package domain.usecases.validator

import core.TransactionCantBeNull
import domain.model.Transaction
import domain.model.Violation
import domain.usecases.UseCase
import domain.usecases.transaction.GetTransactions
import java.time.temporal.ChronoUnit

class ValidateDoubledTransaction(private val getTransactions: GetTransactions): UseCase<Violation?> {

    private val maxInterval = 2L
    private var transaction: Transaction? = null

    override fun execute(): Violation? {
        if (transaction == null) {
            throw TransactionCantBeNull()
        }

        val lastSucceededTransactions = getTransactions.execute()
        val doubleTransactions = lastSucceededTransactions
            .filter { it.time.until(transaction!!.time, ChronoUnit.MINUTES) <= maxInterval }
            .filter { it.merchant == transaction!!.merchant && it.amount == transaction!!.amount }
        val isDoubleTransactionViolated = doubleTransactions.isNotEmpty()
        if (isDoubleTransactionViolated) {
            return Violation.DoubledTransaction
        }

        return null
    }

    fun with(transaction: Transaction): ValidateDoubledTransaction {
        this.transaction = transaction
        return this
    }

}
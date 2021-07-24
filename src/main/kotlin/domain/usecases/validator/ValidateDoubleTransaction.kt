package domain.usecases.validator

import core.TransactionCantBeNull
import domain.model.Transaction
import domain.model.Violation
import domain.usecases.UseCase
import domain.usecases.transaction.GetTransactionsFromMerchant
import java.time.LocalDateTime

class ValidateDoubleTransaction(private val getTransactionsFromMerchant: GetTransactionsFromMerchant): UseCase<Violation?> {

    private val maxInterval = 2L
    private var transaction: Transaction? = null

    override fun execute(): Violation? {
        if (transaction == null) {
            throw TransactionCantBeNull()
        }

        val lastTransactionsFromMerchant = getTransactionsFromMerchant.with(transaction!!.merchant).execute()
        val doubleTransactions = lastTransactionsFromMerchant
            .filter { it.time.isAfter(LocalDateTime.now().minusMinutes(maxInterval)) }
            .filter { it.merchant == transaction!!.merchant && it.amount == transaction!!.amount }
        val isDoubleTransactionViolated = doubleTransactions.isNotEmpty()
        if (isDoubleTransactionViolated) {
            return Violation.DoubleTransaction
        }

        return null
    }

    fun with(transaction: Transaction): ValidateDoubleTransaction {
        this.transaction = transaction
        return this
    }

}
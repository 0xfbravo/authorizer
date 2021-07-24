package domain.usecases.transaction

import data.repository.TransactionRepository
import domain.model.Transaction
import domain.usecases.UseCase

class GetLastTransactions(private val repository: TransactionRepository): UseCase<List<Transaction>> {

    private var merchant: String? = null

    override fun execute(): List<Transaction> {
        return if (merchant == null) {
            repository.getTransactions()
        } else {
            repository.getTransactions(merchant!!)
        }
    }

    fun with(merchant: String): GetLastTransactions {
        this.merchant = merchant
        return this
    }

}
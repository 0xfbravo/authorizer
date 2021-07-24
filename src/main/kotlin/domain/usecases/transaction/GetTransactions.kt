package domain.usecases.transaction

import core.MerchantCantBeNull
import data.repository.TransactionRepository
import domain.model.Transaction
import domain.usecases.UseCase

class GetTransactions(private val repository: TransactionRepository): UseCase<List<Transaction>> {

    private var merchant: String? = null

    override fun execute(): List<Transaction> {
        if (merchant == null) {
            throw MerchantCantBeNull()
        }

        return repository.getTransactions(merchant!!)
    }

    fun with(merchant: String): GetTransactions {
        this.merchant = merchant
        return this
    }

}
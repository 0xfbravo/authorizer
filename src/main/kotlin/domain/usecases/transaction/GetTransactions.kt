package domain.usecases.transaction

import data.repository.TransactionRepository
import domain.model.Transaction
import domain.usecases.UseCase

class GetTransactions(private val repository: TransactionRepository): UseCase<List<Transaction>> {

    override fun execute(): List<Transaction> {
        return repository.getTransactions()
    }

}
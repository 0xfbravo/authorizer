package domain.usecases.transaction

import domain.model.Transaction
import domain.usecases.UseCase

class GetLastTransactions: UseCase<List<Transaction>> {
    override fun execute(): List<Transaction> {
        TODO("Not yet implemented")
    }
}
package data.datasource

import domain.model.Transaction

interface TransactionDataSource {
    fun getTransactions(): List<Transaction>
    fun getTransactions(fromMerchant: String): List<Transaction>
    fun addTransaction(newTransaction: Transaction): Boolean
}

class TransactionDataSourceImpl: TransactionDataSource {
    private val dataSource: HashSet<Transaction> = hashSetOf()

    override fun getTransactions(): List<Transaction> {
        return dataSource.toList()
    }

    override fun getTransactions(fromMerchant: String): List<Transaction> {
        return dataSource.filter { it.merchant == fromMerchant }.toList()
    }

    override fun addTransaction(newTransaction: Transaction): Boolean {
        return dataSource.add(newTransaction)
    }

}
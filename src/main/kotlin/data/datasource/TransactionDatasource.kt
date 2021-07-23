package data.datasource

import domain.model.Transaction

interface TransactionDatasource {
    fun getTransactions(): List<Transaction>
    fun getTransactions(fromMerchant: String): List<Transaction>
    fun getLastTransactions(fromMerchant: String?, withMinutesInterval: Int): List<Transaction>
    fun addTransaction(newTransaction: Transaction): Boolean
}

class TransactionDatasourceImpl: TransactionDatasource {
    private val datasource: HashSet<Transaction> = hashSetOf()

    override fun getTransactions(): List<Transaction> {
        return datasource.toList()
    }

    override fun getTransactions(fromMerchant: String): List<Transaction> {
        return datasource.filter { it.merchant == fromMerchant }.toList()
    }

    override fun addTransaction(newTransaction: Transaction): Boolean {
        return datasource.add(newTransaction)
    }

    override fun getLastTransactions(fromMerchant: String?, withMinutesInterval: Int): List<Transaction> {
        return datasource.toList()
    }

}
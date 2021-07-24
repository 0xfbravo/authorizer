package data.repository

import data.datasource.TransactionDataSource
import domain.model.Transaction

interface TransactionRepository {
    fun getTransactions(): List<Transaction>
    fun getTransactions(fromMerchant: String): List<Transaction>
    fun addTransaction(newTransaction: Transaction): Boolean
}

class TransactionRepositoryImpl(private val dataSource: TransactionDataSource): TransactionRepository {

    override fun getTransactions(): List<Transaction> {
        return dataSource.getTransactions()
    }

    override fun getTransactions(fromMerchant: String): List<Transaction> {
        return dataSource.getTransactions(fromMerchant)
    }

    override fun addTransaction(newTransaction: Transaction): Boolean {
        return dataSource.addTransaction(newTransaction)
    }

}
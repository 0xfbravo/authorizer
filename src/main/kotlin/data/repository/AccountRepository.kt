package data.repository

import data.datasource.AccountDataSource
import domain.model.Account

interface AccountRepository {
    fun getCurrentAccount(): Account?
    fun addAccount(account: Account): Boolean
    fun containsAccount(): Boolean
}

class AccountRepositoryImpl(private val dataSource: AccountDataSource): AccountRepository {

    override fun getCurrentAccount(): Account? {
        return dataSource.getCurrentAccount()
    }

    override fun addAccount(account: Account): Boolean {
        return dataSource.addAccount(account)
    }

    override fun containsAccount(): Boolean {
        return dataSource.containsAccount()
    }

}
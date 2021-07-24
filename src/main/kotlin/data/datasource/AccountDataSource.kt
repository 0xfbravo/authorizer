package data.datasource

import domain.model.Account

interface AccountDataSource {
    fun getCurrentAccount(): Account?
    fun addAccount(account: Account): Boolean
    fun containsAccount(): Boolean
}

class AccountDataSourceImpl: AccountDataSource {
    private val dataSource: ArrayList<Account> = arrayListOf()

    override fun getCurrentAccount(): Account? {
        return dataSource.firstOrNull()
    }

    override fun addAccount(account: Account): Boolean {
        if (containsAccount()) {
            return false
        }
        return dataSource.add(account)
    }

    override fun containsAccount(): Boolean {
        return dataSource.size == 1
    }

}
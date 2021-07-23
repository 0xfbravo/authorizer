package data.datasource

import domain.model.Account

interface AccountDatasource {
    fun getCurrentAccount(): Account
    fun addAccount(account: Account): Boolean
    fun containsAccount(): Boolean
}

class AccountDatasourceImpl: AccountDatasource {
    private val datasource: ArrayList<Account> = arrayListOf()

    override fun getCurrentAccount(): Account {
        return datasource.first()
    }

    override fun addAccount(account: Account): Boolean {
        if (containsAccount()) {
            return false
        }
        return datasource.add(account)
    }

    override fun containsAccount(): Boolean {
        return datasource.size == 1
    }

}
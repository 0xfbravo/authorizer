package domain.usecases.account

import data.repository.AccountRepository
import core.AccountCantBeNull
import core.AccountCantBeUpdated
import domain.model.Account
import domain.usecases.UseCase

class UpdateCurrentAccount(private val repository: AccountRepository): UseCase<Unit> {
    private var account: Account? = null

    override fun execute() {
        if (account == null) {
            throw AccountCantBeNull()
        }

        val isAccountUpdated = repository.updateCurrentAccount(account!!)
        if (!isAccountUpdated) {
            throw AccountCantBeUpdated()
        }
    }

    fun with(account: Account): UpdateCurrentAccount {
        this.account = account
        return this
    }

}
package domain.usecases.account

import core.AccountCantBeNull
import data.repository.AccountRepository
import domain.model.Account
import domain.model.Response
import domain.model.Violation
import domain.usecases.UseCase

class CreateAccount(private val repository: AccountRepository): UseCase<Response> {
    private var account: Account? = null

    override fun execute(): Response {
        if (account == null) {
            throw AccountCantBeNull()
        }

        val isAccountCreated = repository.addAccount(account!!)
        if (!isAccountCreated) {
            val currentAccount = repository.getCurrentAccount()!!
            return Response(currentAccount, listOf(Violation.AccountAlreadyInitialized))
        }

        return Response(account!!, listOf())
    }

    fun with(account: Account): CreateAccount {
        this.account = account
        return this
    }

}
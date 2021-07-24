package domain.usecases

import domain.AccountCantBeNull
import domain.CardNotActive
import domain.model.Account

class ValidateCardActivation: UseCase<Unit> {
    private var account: Account? = null

    override fun execute() {
        if (account == null) {
            throw AccountCantBeNull()
        }

        if (!account!!.activeCard) {
            throw CardNotActive()
        }
    }

    fun with(account: Account): ValidateCardActivation {
        this.account = account
        return this
    }

}
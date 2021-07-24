package domain.usecases.utils

import com.google.gson.Gson
import domain.AccountCantBeNull
import domain.model.Account
import domain.model.Response
import domain.usecases.UseCase

class SendResponse(private val gson: Gson): UseCase<String> {
    private var account: Account? = null
    private var violations: List<String> = listOf()

    override fun execute(): String {
        if (account == null) {
            throw AccountCantBeNull()
        }

        val response = Response(account!!, violations)
        return gson.toJson(response)
    }

    fun with(account: Account, violations: List<String>?): SendResponse {
        this.account = account
        if (violations != null) {
            this.violations = violations
        }
        return this
    }
}
package domain.usecases

import domain.model.Request
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ReadRequest: UseCase<Request?> {
    private var requestString: String? = null

    override fun execute(): Request? {
        if (requestString.isNullOrBlank()) {
            return null
        }
        return Json.decodeFromString<Request>(requestString!!)
    }

    fun with(requestString: String): ReadRequest {
        this.requestString = requestString
        return this
    }
}
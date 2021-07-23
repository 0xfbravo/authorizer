package domain.usecases

import com.google.gson.Gson
import domain.model.Request

class ReadRequest(private val gson: Gson): UseCase<Request?> {
    private var requestString: String? = null

    override fun execute(): Request? {
        if (requestString.isNullOrBlank()) {
            return null
        }
        return try {
            gson.fromJson(requestString!!, Request::class.java)
        } catch (exception: Exception) {
            null
        }
    }

    fun with(requestString: String): ReadRequest {
        this.requestString = requestString
        return this
    }
}
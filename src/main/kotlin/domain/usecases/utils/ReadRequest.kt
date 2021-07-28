package domain.usecases.utils

import com.google.gson.Gson
import domain.model.Request
import domain.usecases.UseCase

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
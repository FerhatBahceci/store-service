package utility.response

sealed class ResponseException : Throwable() {

    abstract override val message: String
    abstract val status: Int

    data class BadRequest(override val message: String, override val status: Int = 400) : ResponseException()

    data class InternalError(override val message: String, override val status: Int = 500) : ResponseException()

    data class NotFound(override val message: String, override val status: Int = 404) : ResponseException()
}

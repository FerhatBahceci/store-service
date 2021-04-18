package utility.request

interface Request<T> : RequestValidator<T>{

    val type : Type
    val expectedStatus : Int

    enum class Type {
        GET,
        POST,
        PUT,
        DELETE
    }
}

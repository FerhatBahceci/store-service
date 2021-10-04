package utility.request

interface Request<T> : RequestValidator<T>{

    val type : Type

    enum class Type(val int : Int) {
        GET(200),
        POST(201),
        PUT(204),
        DELETE(204)
    }
}

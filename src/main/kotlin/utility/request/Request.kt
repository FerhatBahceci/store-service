package utility.request

import io.micronaut.http.HttpStatus

interface Request<T> : RequestValidator<T>{

    val type : Type

    enum class Type(val status  : HttpStatus) {
        GET(HttpStatus.OK),
        POST(HttpStatus.CREATED),
        PUT(HttpStatus.NO_CONTENT),
        DELETE(HttpStatus.NO_CONTENT)
    }
}

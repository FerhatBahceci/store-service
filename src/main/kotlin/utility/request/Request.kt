@file:Suppress("LeakingThis")

package utility.request

interface Request<T> : RequestValidator<T>{

    val type : Type

    enum class Type {
        GET,
        POST,
        PUT,
        DELETE
    }
}

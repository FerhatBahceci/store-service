@file:Suppress("LeakingThis")

package utility.request

interface Request<T> : RequestValidator<T>{

    val type : Type
    val status : Int

    enum class Type {
        GET,
        POST,
        PUT,
        DELETE
    }
}

package utility.request

fun interface RequestValidator<T> {
    fun validate()
}

abstract class Request<T> : RequestValidator<T> {

    init {
        validate()
    }
}



package utility.request

abstract class Request<T> : RequestValidator<T> {

    init {
        validate()
    }
}



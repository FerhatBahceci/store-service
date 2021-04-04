@file:Suppress("LeakingThis")

package utility.request

fun interface RequestValidator<T> {
    fun validate()
}

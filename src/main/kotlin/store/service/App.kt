package store.service

import io.micronaut.runtime.Micronaut

object App {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.run(App::class.java, *args)
    }
}

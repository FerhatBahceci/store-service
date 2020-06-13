package store.viewer

import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import store.service.beans

@SpringBootApplication
class App

@ObsoleteCoroutinesApi
fun main(args: Array<String>) {
    runApplication<App>(*args) {
        addInitializers(beans())
    }
}
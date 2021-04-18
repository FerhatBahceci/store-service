package store.service.model.validators

import kotlinx.serialization.ExperimentalSerializationApi
import store.service.model.Store
import utility.response.ResponseException
import java.lang.IllegalArgumentException
import java.time.DayOfWeek

class OpeningHoursValidator {

    companion object {

        @ExperimentalSerializationApi
        fun Map<String, Store.OpeningHours>.validateHours() {
            this.keys.toList().validateDayOfWeek()
            this.values.validateOpeningHours()
        }

        private fun List<String>.validateDayOfWeek() {
            this.forEach {
                runCatching {
                    DayOfWeek.valueOf(it)
                }.getOrElse {
                    throw ResponseException.BadRequest(it.message ?: "Invalid DayOfWeek: $it")
                }
            }
        }

        @ExperimentalSerializationApi
        private fun Collection<Store.OpeningHours>.validateOpeningHours() {
            this.forEach { if (it.opening?.seconds ?: 0 > it.closing?.seconds ?: 0) throw IllegalArgumentException("Invalid opening hour: $it") }
        }
    }
}

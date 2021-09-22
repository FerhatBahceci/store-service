package store.service.gateway

import kotlinx.serialization.ExperimentalSerializationApi
import utility.response.ResponseException
import java.time.DayOfWeek
import java.util.regex.Pattern

@ExperimentalSerializationApi
fun Store.validateStore() {
    this.phoneNo?.validatePhoneNo()
    this.hours?.validateHours()
    this.coordinates?.validateCoordinates()
}

@ExperimentalSerializationApi
private fun Map<String, Store.OpeningHours>.validateHours() {
    this.keys.toList().validateDayOfWeek()
    this.values.validateOpeningHours()
}

private fun List<String>.validateDayOfWeek() {
    this.forEach {
        runCatching {
            DayOfWeek.valueOf(it)
        }.getOrElse {
            throw ResponseException.BadRequest("Invalid DayOfWeek:" + it.message)
        }
    }
}

@ExperimentalSerializationApi
private fun Collection<Store.OpeningHours>.validateOpeningHours() {
    this.forEach { if (it.opening?.seconds ?: 0 > it.closing?.seconds ?: 0) throw IllegalArgumentException("Invalid opening hour: $it") }
}


@ExperimentalSerializationApi
private fun Store.Coordinates.validateCoordinates() {
    //TODO find out how to validate coordinates properly
}

private fun String.validatePhoneNo() {
    val pattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$")
/*
            if (!pattern.matcher(this).matches()) throw IllegalArgumentException("Invalid telephone number!")
*/
}

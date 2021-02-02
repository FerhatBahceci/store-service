package store.service

import kotlinx.serialization.ExperimentalSerializationApi
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@ExperimentalSerializationApi
data class StoreCollection(
    val description: String? = null,
    val id: String? = null,
    val name: String? = null,
    val hours: List<DailyHour> = emptyList(),
    val phoneNo: String? = null,
    val type: String? = null
) {
    data class DailyHour(val dayOfWeek: String, val opening: String, val closing: String)

    companion object {

        fun Store.mapToCollection() =
            StoreCollection(description = description, id = id, name = name) // TODO continue mapping

        private fun Store.Hours.mapToHours() =
            this.hours.map { it }

        private fun Map.Entry<Store.Hours.DayOfWeek, Store.Hours.OpeningHours>.mapToDailyHour() {
            DailyHour(dayOfWeek = key.name, opening = value.opening.mapToLocalDateTimeString(), closing = value.closing.mapToLocalDateTimeString())
        }

        private fun Store.Hours.OpeningHours.Timestamp.mapToLocalDateTimeString() : String {
            return ""

            //TODO fix mapping back and forth
        }
    }
}

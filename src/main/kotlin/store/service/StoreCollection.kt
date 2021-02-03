package store.service

import kotlinx.serialization.ExperimentalSerializationApi
import java.time.LocalTime

@ExperimentalSerializationApi
data class StoreCollection(
    override val coordinates: Coordinates? = null,
    override val description: String? = null,
    override val id: String? = null,
    override val name: String? = null,
    val hours: List<DailyHour>? = emptyList(),
    override val phoneNo: String? = null,
    val type: String? = null,
) : AbstractStore {

    data class DailyHour(val dayOfWeek: String, val opening: LocalTime?, val closing: LocalTime?)

    companion object {

        fun StoreCollection.mapToStore() =
            Store(
                coordinates = coordinates,
                description = description,
                id = id,
                name = name,
                hours = hours?.mapToHours(),
                phoneNo = phoneNo,
                type = type?.mapToType()
            )

        private fun String.mapToType() = Store.Type.valueOf(this)

        private fun List<DailyHour>.mapToHours() =
            Store.Hours(mutableMapOf<Store.Hours.DayOfWeek, Store.Hours.OpeningHours>()
                .apply {
                    this.forEach { this.put(it.key, it.value) }
                }
            )

        fun Store.mapToStoreCollection() =
            StoreCollection(
                coordinates = coordinates,
                description = description,
                id = id,
                name = name,
                hours = hours?.mapToHoursCollection(),
                phoneNo = phoneNo,
                type = type?.name
            ) // TODO continue mapping


        private fun Store.Hours.mapToHoursCollection() =
            this.hours.map { it.mapToDailyHourCollection() }

        private fun Map.Entry<Store.Hours.DayOfWeek, Store.Hours.OpeningHours>.mapToDailyHourCollection() =
            DailyHour(
                dayOfWeek = key.name,
                opening = value.opening.getLocalTime(),
                closing = value.closing.getLocalTime()
            )
    }
}

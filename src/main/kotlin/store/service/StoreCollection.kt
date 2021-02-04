package store.service

import kotlinx.serialization.*
import utility.proto.Timestamp
import java.time.Instant

@ExperimentalSerializationApi
data class StoreCollection(
    override val description: String? = null,
    override val id: String? = null,
    override val name: String? = null,
    val hours: List<DailyHour>? = emptyList(),
    override val phoneNo: String? = null,
    val type: String? = null,
    override val coordinates: Coordinates? = null,
) : AbstractStore {

    data class DailyHour(val dayOfWeek: String, val opening: Instant?, val closing: Instant?)

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
            Store.Hours(
                sortedMapOf(
                    * this.map { Pair(Store.Hours.DayOfWeek.valueOf(it.dayOfWeek), it.mapToOpeningHours()) }
                        .toTypedArray()
                )
            )

        private fun DailyHour.mapToOpeningHours() =
            Store.Hours.OpeningHours(this.opening?.mapToTimestamp(), this.closing?.mapToTimestamp())

        private fun Instant.mapToTimestamp() = Timestamp(this.epochSecond, this.nano)

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
                opening = value.opening?.mapToInstant(),
                closing = value.closing?.mapToInstant()
            )
    }
}

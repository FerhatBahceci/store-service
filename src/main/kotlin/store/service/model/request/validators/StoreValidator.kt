package store.service.model.request.validators

import kotlinx.serialization.ExperimentalSerializationApi
import store.service.model.Store
import store.service.model.request.validators.CoordinatesValidator.Companion.validateCoordinates
import store.service.model.request.validators.OpeningHoursValidator.Companion.validateHours
import store.service.model.request.validators.PhoneNumberValidator.Companion.validatePhoneNo

class StoreValidator {

    companion object {

        @ExperimentalSerializationApi
        fun validateStore(store: Store) {
            store.phoneNo?.validatePhoneNo()
            store.hours?.validateHours()
            store.coordinates?.validateCoordinates()
        }
    }
}

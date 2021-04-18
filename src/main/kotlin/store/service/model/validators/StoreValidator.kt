package store.service.model.validators

import kotlinx.serialization.ExperimentalSerializationApi
import store.service.model.Store
import store.service.model.validators.CoordinatesValidator.Companion.validateCoordinates
import store.service.model.validators.OpeningHoursValidator.Companion.validateHours
import store.service.model.validators.PhoneNumberValidator.Companion.validatePhoneNo

class StoreValidator {

    companion object {

        @ExperimentalSerializationApi
        fun Store.validateStore() {
            this.phoneNo?.validatePhoneNo()
            this.hours?.validateHours()
            this.coordinates?.validateCoordinates()
        }
    }
}

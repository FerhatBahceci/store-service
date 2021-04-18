package store.service.model.validators

import kotlinx.serialization.ExperimentalSerializationApi
import store.service.model.Store

class CoordinatesValidator {

    companion object {
        @ExperimentalSerializationApi
        fun Store.Coordinates.validateCoordinates() {
            //TODO find out how to validate coordinates properly
        }
    }
}

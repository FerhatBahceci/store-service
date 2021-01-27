package utility

import kotlinx.serialization.Serializable

interface Response<T> {

    // TODO, fix items, data, totalItems etc, status, error (with message). for the response

}

@Serializable
class UnitData : Response<UnitData>

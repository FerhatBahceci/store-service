package store.service.store

import java.time.Instant

data class Store(val closes: Instant,
                 val description: String,
                 val id: String,
                 val name: String,
                 val opens: Instant,
                 val phoneNo: String,
                 val type: Type) {

    enum class Type {
        ACCESSORIES,
        LEISURE,
        BOOKS_MEDIA_ELECTRONICS,
        HEALTH_BEAUTY,
        OPTICIAN,
        HOME_DECORATION,
        TOYS_HOBBY,
        GROCERIES,
        RESTAURANT_CAFE,
        JEWELLERY,
        STAND,
        FASHION,
        SERVICE,
        SPORTS,
    }
}
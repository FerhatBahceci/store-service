package store.service.model.validators

import java.util.regex.Pattern

class PhoneNumberValidator {

    companion object {
        fun String.validatePhoneNo() {
            val pattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$")
/*
            if (!pattern.matcher(this).matches()) throw IllegalArgumentException("Invalid telephone number!")
*/
        }
    }
}

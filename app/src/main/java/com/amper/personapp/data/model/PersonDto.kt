package com.amper.personapp.data.model

import com.amper.personapp.util.DateUtility
import java.io.Serializable
import java.util.Date

data class PersonDto(
    val id: String,
    val picture: Picture? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val birthDay: String? = null,
    val email: String? = null,
    val mobileNumber: String? = null,
    val address: Location? = null,
    val contactPerson: String? = null,
    val contactPersonNumber: String? = null,
): Serializable {
    fun getAge(): Int {
        return birthDay?.run(DateUtility::toDate)?.let {
            DateUtility.getYearsDifference(it, Date())
        } ?: 0
    }
}

fun PersonModel.mapToDto(): PersonDto {
    return PersonDto(
        id = id?.value.orEmpty(),
        picture = picture,
        firstName = name?.firstName,
        lastName = name?.lastName,
        birthDay = dob?.date,
        email = email,
        mobileNumber = cell,
        address = location,
        contactPerson = "",
        contactPersonNumber = phone
    )
}
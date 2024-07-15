package com.amper.personapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class PersonEntity (
    @PrimaryKey val id: String,
    val picture: Picture? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val birthDay: String? = null,
    val email: String? = null,
    val mobileNumber: String? = null,
    val address: Location? = null,
    val contactPerson: String? = null,
    val contactPersonNumber: String? = null,
)

fun PersonEntity.mapToDto(): PersonDto {
    return PersonDto(
        id = id,
        picture = picture,
        firstName = firstName,
        lastName = lastName,
        birthDay = birthDay,
        email = email,
        mobileNumber = mobileNumber,
        address = address,
        contactPerson = contactPersonNumber,
        contactPersonNumber = contactPersonNumber
    )
}

fun PersonModel.mapToEntity(): PersonEntity {
    return PersonEntity(
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
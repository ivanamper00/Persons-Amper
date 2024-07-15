package com.amper.personapp.data.model

import com.google.gson.annotations.SerializedName

data class PersonResultModel (
    @SerializedName("results") val result: List<PersonModel>? = null,
)

data class PersonModel(
    @SerializedName("id") val id: Id? = null,
    @SerializedName("name") val name: Name? = null,
    @SerializedName("location") val location: Location? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("dob") val dob: BirthDate? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("cell") val cell: String? = null,
    @SerializedName("picture") val picture: Picture? = null,
)

data class Id(
    @SerializedName("name") val name: String? = null,
    @SerializedName("value") val value: String? = null,
)

data class Name(
    @SerializedName("first") val firstName: String? = null,
    @SerializedName("last") val lastName: String? = null,
)

data class Location(
    @SerializedName("street") val street: Street? = null,
    @SerializedName("city") val city: String? = null,
    @SerializedName("state") val state: String? = null,
    @SerializedName("country") val country: String? = null,
    @SerializedName("postcode") val postcode: String? = null,
)

data class Street(
    @SerializedName("number") val number: Int? = null,
    @SerializedName("name") val name: String? = null,
)

data class BirthDate(
    @SerializedName("date") val date: String? = null,
)

data class Picture(
    @SerializedName("large") val large: String? = null,
    @SerializedName("medium") val medium: String? = null,
    @SerializedName("thumbnail") val thumbnail: String? = null,
)


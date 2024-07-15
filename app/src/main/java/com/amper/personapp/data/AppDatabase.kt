package com.amper.personapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.amper.personapp.data.model.Location
import com.amper.personapp.data.model.PersonEntity
import com.amper.personapp.data.model.Picture
import com.amper.personapp.data.service.PersonDao
import com.google.gson.Gson

@Database(
    entities = [PersonEntity::class],
     version = 1
)
@TypeConverters(DataConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract val dao: PersonDao
}

class DataConverters {
    @TypeConverter
    fun fromPicture(picture: Picture?): String? {
        return Gson().toJson(picture)
    }

    @TypeConverter
    fun toPicture(str: String?): Picture? {
        return Gson().fromJson(str, Picture::class.java)
    }

    @TypeConverter
    fun fromLocation(loc: Location?): String? {
        return Gson().toJson(loc)
    }

    @TypeConverter
    fun toLocation(str: String?): Location? {
        return Gson().fromJson(str, Location::class.java)
    }
}
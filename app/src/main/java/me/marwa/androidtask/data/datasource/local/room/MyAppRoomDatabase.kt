package me.marwa.androidtask.data.datasource.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.marwa.androidtask.domain.entity.CartEntity
import me.marwa.androidtask.domain.entity.ImageConverter


@Database(entities = [CartEntity::class], version = 1, exportSchema = true)
@TypeConverters(ImageConverter::class)
abstract class MyAppRoomDatabase : RoomDatabase() {

    abstract fun cartDoa(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: MyAppRoomDatabase? = null

        fun getInstance(context: Context): MyAppRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = buildMyDB(context)
                INSTANCE = instance
                INSTANCE!!
            }
        }

        private fun buildMyDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MyAppRoomDatabase::class.java,
                "myApp.db"
            ).fallbackToDestructiveMigration().build()

    }


}
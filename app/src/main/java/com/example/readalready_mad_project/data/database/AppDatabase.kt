package com.example.readalready_mad_project.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Database(
    entities = [BookEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BookDB : RoomDatabase() {
    abstract val bookDao: BookDao // Dao instance so that the DB knows about the Dao

    // declare as singleton - companion objects are like static variables in Java
    companion object {
        @Volatile // never cache the value of instance
        private var instance: BookDB? = null
        fun getDatabase(context: Context): BookDB {
            return instance
                ?: synchronized(this) { // wrap in synchronized block to prevent race conditions
                    Room.databaseBuilder(context, BookDB::class.java, "book_db")
                        .fallbackToDestructiveMigration() // if schema changes wipe the whole db - there are better migration strategies for production usage
                        .build() // create an instance of the db
                        .also {
                            instance = it // override the instance with newly created db
                        }
                }
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BookDB {
        return BookDB.getDatabase(context)
    }

    @Provides
    fun provideBookDao(db: BookDB): BookDao {
        return db.bookDao
    }
}
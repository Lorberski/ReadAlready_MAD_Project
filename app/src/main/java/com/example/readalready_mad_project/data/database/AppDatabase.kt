package com.example.readalready_mad_project.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(entities = [BookEntity::class], version = 1, exportSchema = false)
//abstract class AppDatabase : RoomDatabase() {
//
//    // Die DAO, die für den Zugriff auf die Tabelle 'books' zuständig ist
//    abstract fun bookDao(): BookDao
//
//    companion object {
//
//        // Singleton-Instanz der Datenbank
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        // Erstellt Datenbank falls sie noch nicht existiert
//        fun getDatabase(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "already_read_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}


//val db = AppDatabase.getDatabase(context)
//val bookDao = db.bookDao()
//
//// Ein Buch aus der Datenbank abrufen
//val book = bookDao.getBookById(1)
//
//// Ein Buch hinzufügen
//val newBook = BookEntity(
//    title = "Das Java Handbuch",
//    author = "John Doe",
//    imageUrl = "https://example.com/image.jpg",
//    description = "Ein umfassendes Buch über Java.",
//    rating = 4.5f
//)
//bookDao.insertBook(newBook)

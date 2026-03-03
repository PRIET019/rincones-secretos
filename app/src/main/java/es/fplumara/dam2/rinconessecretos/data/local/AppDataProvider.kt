package es.fplumara.dam2.rinconessecretos.data.local

import android.content.Context
import androidx.room.Room

object AppDatabaseProvider {

    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "rincones_db"
            ).build().also { INSTANCE = it }
        }
    }
}

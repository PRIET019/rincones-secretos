package es.fplumara.dam2.rinconessecretos.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RinconEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun rinconDao(): RinconDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "rincones_db"
                )
                    .fallbackToDestructiveMigration() // para evitar crashes por version 2
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }


}

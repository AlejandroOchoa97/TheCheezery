package ochoa.ivan.thecheezery_ivanochoa.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ochoa.ivan.thecheezery_ivanochoa.data.dao.ComboDao
import ochoa.ivan.thecheezery_ivanochoa.data.dao.ProductComboDao
import ochoa.ivan.thecheezery_ivanochoa.data.dao.ProductDao
import ochoa.ivan.thecheezery_ivanochoa.data.entity.ComboEntity
import ochoa.ivan.thecheezery_ivanochoa.data.entity.ProductComboEntity
import ochoa.ivan.thecheezery_ivanochoa.data.entity.ProductEntity

@Database(
    entities = [
        ProductEntity::class,
        ComboEntity::class,
        ProductComboEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun comboDao(): ComboDao
    abstract fun productComboDao(): ProductComboDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cheezery.db"
                )
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

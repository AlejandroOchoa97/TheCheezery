package ochoa.ivan.thecheezery_ivanochoa.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ochoa.ivan.thecheezery_ivanochoa.data.CheezeryContract.ProductsEntry

class DatabaseHelper(context : Context) :
    SQLiteOpenHelper (context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME= "cheezery.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("PRAGMA foreign_keys = ON")

        db.execSQL(
            """CREATE TABLE ${ProductsEntry.TABLE_NAME} ( 
             ${ProductsEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, 
             ${ProductsEntry.COLUMN_NAME} TEXT NOT NULL,
             ${ProductsEntry.COLUMN_IMAGE} TEXT,
             ${ProductsEntry.COLUMN_PRICE} REAL NOT NULL,
             ${ProductsEntry.COLUMN_DESCRIPTION} TEXT
             )
             """.trimIndent()
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS ${ProductsEntry.TABLE_NAME}")
    }

}
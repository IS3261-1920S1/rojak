package com.example.rojak.database.FoodTransaction

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FoodTransactionDbHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FoodTransactionData.db"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${FoodTransactionContract.FoodTransactionEntry.TABLE_NAME} (" +
                    "${FoodTransactionContract.FoodTransactionEntry.COLUMN_NAME_TRANSACTION_ID} SERIAL PRIMARY KEY," +
                    "${FoodTransactionContract.FoodTransactionEntry.COLUMN_NAME_FOOD_ID} INTEGER FOREIGN KEYS REFERENCES Foods(id)," +
                    "${FoodTransactionContract.FoodTransactionEntry.COLUMN_NAME_AMOUNT_PAID} NUMERIC" +
                    "${FoodTransactionContract.FoodTransactionEntry.COLUMN_NAME_TRANSACTION_TIMESTAMP} TIMESTAMP);"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS" +
                " ${FoodTransactionContract.FoodTransactionEntry.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}
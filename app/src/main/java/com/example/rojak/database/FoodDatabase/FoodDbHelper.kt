package com.example.rojak.database.FoodDatabase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FoodDbHelper (context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FoodData.db"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${FoodContract.FoodEntry.TABLE_NAME} (" +
                    "${FoodContract.FoodEntry.COLUMN_NAME_FOOD_ID} SERIAL PRIMARY KEY," +
                    "${FoodContract.FoodEntry.COLUMN_NAME_BASE_AMOUNT} NUMERIC" +
                    "${FoodContract.FoodEntry.COLUMN_NAME_HEALTHYNESS_RATING} NUMERIC);"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS" +
                " ${FoodContract.FoodEntry.TABLE_NAME}"
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
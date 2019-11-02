package com.example.rojak.database.TopUpDatabase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TopUpDbHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "TopUp.db"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${TopUpContract.TopUpEntry.TABLE_NAME} (" +
                    "${TopUpContract.TopUpEntry.COLUMN_NAME_TOPUP_ID} SERIAL PRIMARY KEY," +
                    "${TopUpContract.TopUpEntry.COLUMN_NAME_TOPUP_AMOUNT} NUMERIC," +
                    "${TopUpContract.TopUpEntry.COLUMN_NAME_TOPUP_TIMESTAMP} TIMESTAMP);"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS" +
                " ${TopUpContract.TopUpEntry.TABLE_NAME}"
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
package com.example.rojak.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    private val db: SQLiteDatabase = readableDatabase

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Rojak.db"

        /**
         * Foods Table Default Queries
         */
        private const val CREATE_FOODS_TABLE =
            "CREATE TABLE ${DatabaseContracts.FoodEntry.TABLE_NAME} (" +
                    "${DatabaseContracts.FoodEntry.COLUMN_NAME_FOOD_ID} SERIAL PRIMARY KEY," +
                    "${DatabaseContracts.FoodEntry.COLUMN_NAME_BASE_AMOUNT} NUMERIC" +
                    "${DatabaseContracts.FoodEntry.COLUMN_NAME_HEALTHYNESS_RATING} NUMERIC);"

        private const val DELETE_FOODS_TABLE = "DROP TABLE IF EXISTS" +
                " ${DatabaseContracts.FoodEntry.TABLE_NAME};"

        /**
         * WalletTransaction Table Default Queries
         */

        private const val INITIAL_WALLET_VALUE : Float = 0.0f

        private const val CREATE_WALLET_TRANSACTION_TABLE =
            "CREATE TABLE ${DatabaseContracts.WalletTransactionEntry.TABLE_NAME} (" +
                "${DatabaseContracts.WalletTransactionEntry.COLUMN_NAME_TRANSACTION_ID} SERIAL PRIMARY KEY," +
                "${DatabaseContracts.WalletTransactionEntry.COLUMN_NAME_CURRENT_WALLET_AMOUNT} NUMERIC);"

        private const val DELETE_WALLET_TRANSACTION_TABLE = "DROP TABLE IF EXISTS" +
                " ${DatabaseContracts.WalletTransactionEntry.TABLE_NAME};"

        private const val INITIALIZE_WALLET = "INSERT INTO ${DatabaseContracts.WalletTransactionEntry.TABLE_NAME} " +
                "(${DatabaseContracts.WalletTransactionEntry.COLUMN_NAME_CURRENT_WALLET_AMOUNT}) VALUES" +
                "(0.0);"

        private const val GET_ALL_WALLET_ENTRIES = "SELECT ${DatabaseContracts.WalletTransactionEntry.COLUMN_NAME_CURRENT_WALLET_AMOUNT} " +
                "FROM ${DatabaseContracts.WalletTransactionEntry.TABLE_NAME};"


        /**
         * FoodTransaction Table Default Queries
         */
        private const val CREATE_FOOD_TRANSACTION_TABLE =
            "CREATE TABLE ${DatabaseContracts.FoodTransactionEntry.TABLE_NAME} (" +
                    "${DatabaseContracts.FoodTransactionEntry.COLUMN_NAME_TRANSACTION_ID} INTEGER PRIMARY KEY," +
                    "${DatabaseContracts.FoodTransactionEntry.COLUMN_NAME_FOOD_ID} INTEGER," +
                    "${DatabaseContracts.FoodTransactionEntry.COLUMN_NAME_AMOUNT_PAID} NUMERIC," +
                    "${DatabaseContracts.FoodTransactionEntry.COLUMN_NAME_TRANSACTION_TIMESTAMP} TIMESTAMP, " +
                    "FOREIGN KEY (${DatabaseContracts.FoodTransactionEntry.COLUMN_NAME_TRANSACTION_ID}) REFERENCES " +
                    "${DatabaseContracts.WalletTransactionEntry.TABLE_NAME} (${DatabaseContracts.WalletTransactionEntry.COLUMN_NAME_TRANSACTION_ID})," +
                    "FOREIGN KEY (${DatabaseContracts.FoodTransactionEntry.COLUMN_NAME_FOOD_ID}) REFERENCES ${DatabaseContracts.FoodEntry.TABLE_NAME} (${DatabaseContracts.FoodEntry.COLUMN_NAME_FOOD_ID}));"

        private const val DELETE_FOOD_TRANSACTION_TABLE = "DROP TABLE IF EXISTS" +
                " ${DatabaseContracts.FoodTransactionEntry.TABLE_NAME};"

        /**
         * TopUpTransaction Table Default Queries
         */
        private const val CREATE_TOPUP_TRANSACTION_TABLE =
            "CREATE TABLE ${DatabaseContracts.TopUpTransactionEntry.TABLE_NAME} (" +
                    "${DatabaseContracts.TopUpTransactionEntry.COLUMN_NAME_TRANSACTION_ID} INTEGER PRIMARY KEY," +
                    "${DatabaseContracts.TopUpTransactionEntry.COLUMN_NAME_TOPUP_AMOUNT} NUMERIC," +
                    "${DatabaseContracts.TopUpTransactionEntry.COLUMN_NAME_TOPUP_TIMESTAMP} TIMESTAMP, " +
                    "CONSTRAINT TT_WT_LINK " +
                    "FOREIGN KEY (${DatabaseContracts.TopUpTransactionEntry.COLUMN_NAME_TRANSACTION_ID}) REFERENCES " +
                    "${DatabaseContracts.WalletTransactionEntry.TABLE_NAME} (${DatabaseContracts.WalletTransactionEntry.COLUMN_NAME_TRANSACTION_ID}));"

        private const val DELETE_TOPUP_TRANSACTION_TABLE = "DROP TABLE IF EXISTS" +
                " ${DatabaseContracts.TopUpTransactionEntry.TABLE_NAME};"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_FOODS_TABLE)
        db.execSQL(CREATE_WALLET_TRANSACTION_TABLE)
        db.execSQL(INITIALIZE_WALLET)
        db.execSQL(CREATE_TOPUP_TRANSACTION_TABLE)
        db.execSQL(CREATE_FOOD_TRANSACTION_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(DELETE_FOOD_TRANSACTION_TABLE)
        db.execSQL(DELETE_TOPUP_TRANSACTION_TABLE)
        db.execSQL(DELETE_WALLET_TRANSACTION_TABLE)
        db.execSQL(DELETE_FOODS_TABLE)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }


    /**
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * **********************************
     * WALLET TRANSACTION TABLE FUNCTIONS
     * **********************************
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    fun getCurrentWalletAmount() : Float {
        val cursor = this.db.rawQuery(GET_ALL_WALLET_ENTRIES, null)
        if (cursor.moveToFirst()) {
            val currentWalletAmountString = cursor.getFloat(cursor.getColumnIndex(DatabaseContracts.WalletTransactionEntry.COLUMN_NAME_CURRENT_WALLET_AMOUNT))
            Log.i("CursorTest", currentWalletAmountString.toString())
            return currentWalletAmountString
        }
        cursor.close()
        return -1.0f
    }
}
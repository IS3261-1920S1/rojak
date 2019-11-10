package com.example.rojak.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.rojak.models.FoodTransaction
import com.example.rojak.models.TopUpTransaction
import com.example.rojak.models.Transaction
import org.json.JSONObject

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 16
        const val DATABASE_NAME = "Rojak.db"

        /**
         * Foods Table Default Queries
         */
        private const val CREATE_FOODS_TABLE =
            "CREATE TABLE ${DatabaseContracts.FoodEntry.TABLE_NAME} (" +
                    "${DatabaseContracts.FoodEntry.COLUMN_NAME_FOOD_NAME} TEXT," +
                    "${DatabaseContracts.FoodEntry.COLUMN_NAME_BASE_AMOUNT} NUMERIC," +
                    "${DatabaseContracts.FoodEntry.COLUMN_NAME_HEALTHYNESS_RATING} NUMERIC);"

        private const val DELETE_FOODS_TABLE = "DROP TABLE IF EXISTS" +
                " ${DatabaseContracts.FoodEntry.TABLE_NAME};"

        private val FOOD_POPULATION_QUERIES : Array<String> = arrayOf(
            "INSERT INTO ${DatabaseContracts.FoodEntry.TABLE_NAME} " +
                "(${DatabaseContracts.FoodEntry.COLUMN_NAME_FOOD_NAME}, ${DatabaseContracts.FoodEntry.COLUMN_NAME_BASE_AMOUNT}," +
                "${DatabaseContracts.FoodEntry.COLUMN_NAME_HEALTHYNESS_RATING}) VALUES ('Laksa', 4.50, 0.67);",

            "INSERT INTO ${DatabaseContracts.FoodEntry.TABLE_NAME} " +
                    "(${DatabaseContracts.FoodEntry.COLUMN_NAME_FOOD_NAME}, ${DatabaseContracts.FoodEntry.COLUMN_NAME_BASE_AMOUNT}," +
                    "${DatabaseContracts.FoodEntry.COLUMN_NAME_HEALTHYNESS_RATING}) VALUES ('Chicken Rice', 3.50, 0.53);",

            "INSERT INTO ${DatabaseContracts.FoodEntry.TABLE_NAME} " +
                    "(${DatabaseContracts.FoodEntry.COLUMN_NAME_FOOD_NAME}, ${DatabaseContracts.FoodEntry.COLUMN_NAME_BASE_AMOUNT}," +
                    "${DatabaseContracts.FoodEntry.COLUMN_NAME_HEALTHYNESS_RATING}) VALUES ('Chicken Burger', 4.00, 0.46);",

            "INSERT INTO ${DatabaseContracts.FoodEntry.TABLE_NAME} " +
                    "(${DatabaseContracts.FoodEntry.COLUMN_NAME_FOOD_NAME}, ${DatabaseContracts.FoodEntry.COLUMN_NAME_BASE_AMOUNT}," +
                    "${DatabaseContracts.FoodEntry.COLUMN_NAME_HEALTHYNESS_RATING}) VALUES ('Salmon Pasta', 6.50, 0.49);"
        )






        /**
         * WalletTransaction Table Default Queries
         */

        private const val INITIAL_WALLET_VALUE : Float = 0.0f

        private const val CREATE_WALLET_TRANSACTION_TABLE =
            "CREATE TABLE ${DatabaseContracts.WalletTransactionEntry.TABLE_NAME} (" +
                "${DatabaseContracts.WalletTransactionEntry.COLUMN_NAME_CURRENT_WALLET_AMOUNT} NUMERIC," +
                "${DatabaseContracts.WalletTransactionEntry.COLUMN_NAME_TRANSACTION_TIMESTAMP} TIMESTAMP DEFAULT CURRENT_TIMESTAMP);"

        private const val DELETE_WALLET_TRANSACTION_TABLE = "DROP TABLE IF EXISTS" +
                " ${DatabaseContracts.WalletTransactionEntry.TABLE_NAME};"

        private const val INITIALIZE_WALLET = "INSERT INTO ${DatabaseContracts.WalletTransactionEntry.TABLE_NAME} " +
                "(${DatabaseContracts.WalletTransactionEntry.COLUMN_NAME_CURRENT_WALLET_AMOUNT}) VALUES" +
                "(${INITIAL_WALLET_VALUE});"

        private const val GET_ALL_WALLET_ENTRIES = "SELECT * " +
                "FROM ${DatabaseContracts.WalletTransactionEntry.TABLE_NAME};"


        /**
         * FoodTransaction Table Default Queries
         */
        private const val CREATE_FOOD_TRANSACTION_TABLE =
            "CREATE TABLE ${DatabaseContracts.FoodTransactionEntry.TABLE_NAME} (" +
                    "${DatabaseContracts.FoodTransactionEntry.COLUMN_NAME_TRANSACTION_ID} INTEGER PRIMARY KEY," +
                    "${DatabaseContracts.FoodTransactionEntry.COLUMN_NAME_FOOD_ID} INTEGER," +
                    "${DatabaseContracts.FoodTransactionEntry.COLUMN_NAME_AMOUNT_PAID} NUMERIC, " +
                    "FOREIGN KEY (${DatabaseContracts.FoodTransactionEntry.COLUMN_NAME_TRANSACTION_ID}) REFERENCES " +
                    "${DatabaseContracts.WalletTransactionEntry.TABLE_NAME} (${DatabaseContracts.ROWID})," +
                    "FOREIGN KEY (${DatabaseContracts.FoodTransactionEntry.COLUMN_NAME_FOOD_ID}) REFERENCES ${DatabaseContracts.FoodEntry.TABLE_NAME} (${DatabaseContracts.ROWID}));"

        private const val DELETE_FOOD_TRANSACTION_TABLE = "DROP TABLE IF EXISTS" +
                " ${DatabaseContracts.FoodTransactionEntry.TABLE_NAME};"

        private const val GET_ALL_FOOD_TRANSACTION_MODEL = "SELECT * FROM ${DatabaseContracts.FoodTransactionEntry.TABLE_NAME} INNER JOIN ${DatabaseContracts.WalletTransactionEntry.TABLE_NAME} " +
                "ON ${DatabaseContracts.FoodTransactionEntry.TABLE_NAME}.${DatabaseContracts.FoodTransactionEntry.COLUMN_NAME_TRANSACTION_ID} = ${DatabaseContracts.WalletTransactionEntry.TABLE_NAME}.${DatabaseContracts.ROWID} " +
                "INNER JOIN ${DatabaseContracts.FoodEntry.TABLE_NAME} " +
                "ON ${DatabaseContracts.FoodTransactionEntry.TABLE_NAME}.${DatabaseContracts.FoodTransactionEntry.COLUMN_NAME_FOOD_ID} = ${DatabaseContracts.FoodEntry.TABLE_NAME}.${DatabaseContracts.ROWID};"

        /**
         * TopUpTransaction Table Default Queries
         */
        private const val CREATE_TOPUP_TRANSACTION_TABLE =
            "CREATE TABLE ${DatabaseContracts.TopUpTransactionEntry.TABLE_NAME} (" +
                    "${DatabaseContracts.TopUpTransactionEntry.COLUMN_NAME_TRANSACTION_ID} INTEGER PRIMARY KEY," +
                    "${DatabaseContracts.TopUpTransactionEntry.COLUMN_NAME_TOPUP_AMOUNT} NUMERIC, " +
                    "CONSTRAINT TT_WT_LINK " +
                    "FOREIGN KEY (${DatabaseContracts.TopUpTransactionEntry.COLUMN_NAME_TRANSACTION_ID}) REFERENCES " +
                    "${DatabaseContracts.WalletTransactionEntry.TABLE_NAME} (${DatabaseContracts.ROWID}));"

        private const val DELETE_TOPUP_TRANSACTION_TABLE = "DROP TABLE IF EXISTS" +
                " ${DatabaseContracts.TopUpTransactionEntry.TABLE_NAME};"

        private const val GET_ALL_TOPUP_TRANSACTION_MODEL = "SELECT * FROM ${DatabaseContracts.TopUpTransactionEntry.TABLE_NAME} INNER JOIN ${DatabaseContracts.WalletTransactionEntry.TABLE_NAME} ON " +
                "${DatabaseContracts.TopUpTransactionEntry.TABLE_NAME}.${DatabaseContracts.TopUpTransactionEntry.COLUMN_NAME_TRANSACTION_ID} = ${DatabaseContracts.WalletTransactionEntry.TABLE_NAME}.${DatabaseContracts.ROWID};"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_FOODS_TABLE)
        this.populateFoodEntries(db)
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

    private fun populateFoodEntries(db: SQLiteDatabase) {
        db.beginTransaction()
        try {
            for (query in FOOD_POPULATION_QUERIES) {
                db.execSQL(query)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }
    /**
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * **********************************
     * QR TRANSACTION TABLE FUNCTIONS
     * **********************************
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    fun queryFood(foodID: String) : JSONObject{
        var foodname =""
        var foodid = ""
        var foodprice = ""
        var foodrating = 0.0f
        val query = "SELECT * " +
                "FROM ${DatabaseContracts.FoodEntry.TABLE_NAME} WHERE rowid=${foodID.toInt()};"
             //   "FROM ${DatabaseContracts.FoodEntry.TABLE_NAME} WHERE rowid=" + foodID + ";"
        val cursor = this.readableDatabase.rawQuery(query, null)
        if (cursor.moveToLast()) {
            val name = cursor.getString(cursor.getColumnIndex(DatabaseContracts.FoodEntry.COLUMN_NAME_FOOD_NAME))
            val id = cursor.getLong(0)
            val price = cursor.getString(cursor.getColumnIndex(DatabaseContracts.FoodEntry.COLUMN_NAME_BASE_AMOUNT))
            val rating = cursor.getFloat(cursor.getColumnIndex(DatabaseContracts.FoodEntry.COLUMN_NAME_HEALTHYNESS_RATING))

            foodname = name
            foodid = id.toString()
            foodprice = price
            foodrating = rating
        }else{
            foodname =  "unknown"
        }
        cursor.close()

        val dataSet = JSONObject()
        dataSet.put("food_name", foodname)
        dataSet.put("food_id", foodid)
        dataSet.put("food_price", foodprice)
        dataSet.put("food_rating", foodrating)

        return dataSet
    }

    /**
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * **********************************
     * FOOD TRANSACTION TABLE FUNCTIONS
     * **********************************
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    fun addPurchase(price: Float, foodID: Int) {
        val db = writableDatabase

        val newWalletAmount = this.getCurrentWalletAmount() - price
        val values = ContentValues()
        values.put(DatabaseContracts.WalletTransactionEntry.COLUMN_NAME_CURRENT_WALLET_AMOUNT, newWalletAmount)
        val newId = db.insert(DatabaseContracts.WalletTransactionEntry.TABLE_NAME, null, values)

        val topUpValues = ContentValues()
        topUpValues.put(DatabaseContracts.FoodTransactionEntry.COLUMN_NAME_TRANSACTION_ID, newId)
        topUpValues.put(DatabaseContracts.FoodTransactionEntry.COLUMN_NAME_FOOD_ID, foodID)
        topUpValues.put(DatabaseContracts.FoodTransactionEntry.COLUMN_NAME_AMOUNT_PAID, price)
        db.insert(DatabaseContracts.FoodTransactionEntry.TABLE_NAME, null, topUpValues)
    }

    fun getAvgWeeklyRating() : Float {
        var rating = -1f
        val query = "SELECT avg(rating) FROM ${DatabaseContracts.FoodTransactionEntry.TABLE_NAME} INNER JOIN ${DatabaseContracts.WalletTransactionEntry.TABLE_NAME} " +
                   "ON ${DatabaseContracts.FoodTransactionEntry.TABLE_NAME}.${DatabaseContracts.FoodTransactionEntry.COLUMN_NAME_TRANSACTION_ID} = ${DatabaseContracts.WalletTransactionEntry.TABLE_NAME}.${DatabaseContracts.ROWID} " +
                  "INNER JOIN ${DatabaseContracts.FoodEntry.TABLE_NAME} " +
                    "ON ${DatabaseContracts.FoodTransactionEntry.TABLE_NAME}.${DatabaseContracts.FoodTransactionEntry.COLUMN_NAME_FOOD_ID} = ${DatabaseContracts.FoodEntry.TABLE_NAME}.${DatabaseContracts.ROWID} Limit 5;"

        println(query)
        val cursor = this.readableDatabase.rawQuery(query, null)
        if (cursor.moveToLast()) {
            println("count")
            println(cursor.columnCount)

            println("avg(${DatabaseContracts.FoodEntry.COLUMN_NAME_HEALTHYNESS_RATING})")
            println(cursor.getColumnIndex("AVG(${DatabaseContracts.FoodEntry.COLUMN_NAME_HEALTHYNESS_RATING})"))
            println(cursor.getFloat(cursor.getColumnIndex("avg(${DatabaseContracts.FoodEntry.COLUMN_NAME_HEALTHYNESS_RATING})")))
            rating = cursor.getFloat(cursor.getColumnIndex("avg(${DatabaseContracts.FoodEntry.COLUMN_NAME_HEALTHYNESS_RATING})"))
        }
        cursor.close()
        return rating
    }





    /**
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * **********************************
     * WALLET TRANSACTION TABLE FUNCTIONS
     * **********************************
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */




    fun getCurrentWalletAmount() : Float {
        val cursor = this.readableDatabase.rawQuery(GET_ALL_WALLET_ENTRIES, null)
        if (cursor.moveToLast()) {
            val currentWalletAmountString = cursor.getFloat(cursor.getColumnIndex(DatabaseContracts.WalletTransactionEntry.COLUMN_NAME_CURRENT_WALLET_AMOUNT))
            return currentWalletAmountString
        }
        cursor.close()
        return -1.0f
    }

    fun topUpWallet(topUpAmount : Float) {
        val db = writableDatabase

        val newWalletAmount = this.getCurrentWalletAmount() + topUpAmount
        val values = ContentValues()
        values.put(DatabaseContracts.WalletTransactionEntry.COLUMN_NAME_CURRENT_WALLET_AMOUNT, newWalletAmount)
        val newId = db.insert(DatabaseContracts.WalletTransactionEntry.TABLE_NAME, null, values)

        val topUpValues = ContentValues()
        topUpValues.put(DatabaseContracts.TopUpTransactionEntry.COLUMN_NAME_TRANSACTION_ID, newId)
        topUpValues.put(DatabaseContracts.TopUpTransactionEntry.COLUMN_NAME_TOPUP_AMOUNT, topUpAmount)
        db.insert(DatabaseContracts.TopUpTransactionEntry.TABLE_NAME, null, topUpValues)
    }

    fun getAllWalletTransactions() : ArrayList<Transaction> {
        val db = readableDatabase
        val transactions = ArrayList<Transaction>()

        val cursorFoodTransactionModel = db.rawQuery(GET_ALL_FOOD_TRANSACTION_MODEL, null)
        cursorFoodTransactionModel.use {
            while (it.moveToNext()) {
                transactions.add(
                    FoodTransaction(
                        it.getInt(it.getColumnIndex(DatabaseContracts.FoodTransactionEntry.COLUMN_NAME_TRANSACTION_ID)),
                        it.getFloat(it.getColumnIndex(DatabaseContracts.WalletTransactionEntry.COLUMN_NAME_CURRENT_WALLET_AMOUNT)),
                        it.getString(it.getColumnIndex(DatabaseContracts.WalletTransactionEntry.COLUMN_NAME_TRANSACTION_TIMESTAMP)),
                        it.getString(it.getColumnIndex(DatabaseContracts.FoodEntry.COLUMN_NAME_FOOD_NAME)),
                        it.getFloat(it.getColumnIndex(DatabaseContracts.FoodTransactionEntry.COLUMN_NAME_AMOUNT_PAID))
                    )
                )
            }
        }

        val cursorTopUpTransactionModel =  db.rawQuery(GET_ALL_TOPUP_TRANSACTION_MODEL, null)
        cursorTopUpTransactionModel.use {
            while (it.moveToNext()) {
                transactions.add(
                    TopUpTransaction(
                        it.getInt(it.getColumnIndex(DatabaseContracts.TopUpTransactionEntry.COLUMN_NAME_TRANSACTION_ID)),
                        it.getFloat(it.getColumnIndex(DatabaseContracts.WalletTransactionEntry.COLUMN_NAME_CURRENT_WALLET_AMOUNT)),
                        it.getString(it.getColumnIndex(DatabaseContracts.WalletTransactionEntry.COLUMN_NAME_TRANSACTION_TIMESTAMP)),
                        it.getFloat(it.getColumnIndex(DatabaseContracts.TopUpTransactionEntry.COLUMN_NAME_TOPUP_AMOUNT))
                    )
                )
            }
        }

        return transactions
    }
}
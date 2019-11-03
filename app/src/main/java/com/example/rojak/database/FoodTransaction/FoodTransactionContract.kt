package com.example.rojak.database.FoodTransaction

import android.provider.BaseColumns

object FoodTransactionContract {
    // Table contents are grouped together in an anonymous object.
    object FoodTransactionEntry : BaseColumns {
        const val TABLE_NAME = "FoodTransactions"
        const val COLUMN_NAME_TRANSACTION_ID = "id"
        const val COLUMN_NAME_FOOD_ID = "food_id"
        const val COLUMN_NAME_AMOUNT_PAID = "amount"
        const val COLUMN_NAME_TRANSACTION_TIMESTAMP = "timestamp"
    }
}
package com.example.rojak.database.FoodTransaction

import android.provider.BaseColumns

object FoodTransactionContract {
    // Table contents are grouped together in an anonymous object.
    object FoodTransactionEntry : BaseColumns {
        const val TABLE_NAME = "FoodTransaction"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_FOOD_ID = "food_id"
        const val COLUMN_NAME_AMOUNT = "amount"
    }


}
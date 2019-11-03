package com.example.rojak.database.FoodDatabase

import android.provider.BaseColumns

object FoodContract {
    object FoodEntry : BaseColumns {
        const val TABLE_NAME = "Foods"
        const val COLUMN_NAME_FOOD_ID = "id"
        const val COLUMN_NAME_BASE_AMOUNT = "amount"
        const val COLUMN_NAME_HEALTHYNESS_RATING = "rating"
    }
}
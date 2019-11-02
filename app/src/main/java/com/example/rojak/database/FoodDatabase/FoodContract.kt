package com.example.rojak.database.FoodDatabase

import android.provider.BaseColumns

object FoodContract {
    object FoodEntry : BaseColumns {
        const val TABLE_NAME = "Foods"
        const val COLUMN_NAME_FOOD_ID = "id"
        const val COLUMN_NAME_CALORIES = "calories"
        const val COLUMN_NAME_SUGAR = "sugar"
    }
}
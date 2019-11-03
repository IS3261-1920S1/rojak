package com.example.rojak.database

import android.provider.BaseColumns

object DatabaseContracts {

    object FoodEntry : BaseColumns {
        const val TABLE_NAME = "Foods"
        const val COLUMN_NAME_FOOD_ID = "food_id"
        const val COLUMN_NAME_BASE_AMOUNT = "base_amount"
        const val COLUMN_NAME_HEALTHYNESS_RATING = "rating"
    }

    object WalletTransactionEntry : BaseColumns {
        const val TABLE_NAME = "WalletTransactions"
        const val COLUMN_NAME_TRANSACTION_ID = "transaction_id"
        const val COLUMN_NAME_CURRENT_WALLET_AMOUNT = "wallet_amount"
    }

    object FoodTransactionEntry : BaseColumns {
        const val TABLE_NAME = "FoodTransactions"
        const val COLUMN_NAME_TRANSACTION_ID = "transaction_id"
        const val COLUMN_NAME_FOOD_ID = "food_id"
        const val COLUMN_NAME_AMOUNT_PAID = "paid_amount"
        const val COLUMN_NAME_TRANSACTION_TIMESTAMP = "timestamp"
    }

    object TopUpTransactionEntry : BaseColumns {
        const val TABLE_NAME = "TopUpTransaction"
        const val COLUMN_NAME_TRANSACTION_ID = "transaction_id"
        const val COLUMN_NAME_TOPUP_AMOUNT = "topup_amount"
        const val COLUMN_NAME_TOPUP_TIMESTAMP = "topup_time"
    }

}
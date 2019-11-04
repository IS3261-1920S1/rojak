package com.example.rojak.database

import android.provider.BaseColumns

object DatabaseContracts {

    const val ROWID = "rowid"

    object FoodEntry : BaseColumns {
        const val TABLE_NAME = "Foods"
        const val COLUMN_NAME_FOOD_NAME = "food_name"
        const val COLUMN_NAME_BASE_AMOUNT = "base_amount"
        const val COLUMN_NAME_HEALTHYNESS_RATING = "rating"
    }

    object WalletTransactionEntry : BaseColumns {
        const val TABLE_NAME = "WalletTransactions"
        const val COLUMN_NAME_CURRENT_WALLET_AMOUNT = "wallet_amount"
        const val COLUMN_NAME_TRANSACTION_TIMESTAMP = "timestamp"
    }

    object FoodTransactionEntry : BaseColumns {
        const val TABLE_NAME = "FoodTransactions"
        const val COLUMN_NAME_TRANSACTION_ID = "transaction_id"
        const val COLUMN_NAME_FOOD_ID = "food_id"
        const val COLUMN_NAME_AMOUNT_PAID = "paid_amount"
    }

    object TopUpTransactionEntry : BaseColumns {
        const val TABLE_NAME = "TopUpTransactions"
        const val COLUMN_NAME_TRANSACTION_ID = "transaction_id"
        const val COLUMN_NAME_TOPUP_AMOUNT = "topup_amount"
    }

}
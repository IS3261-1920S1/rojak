package com.example.rojak.database.topupDb

import android.provider.BaseColumns

object TopUpContract {
    object TopUpEntry : BaseColumns {
        const val TABLE_NAME = "TopUps"
        const val COLUMN_NAME_TOPUP_ID = "id"
        const val COLUMN_NAME_TOPUP_AMOUNT = "topup_amount"
        const val COLUMN_NAME_TOPUP_TIMESTAMP = "topup_time"
        const val COLUMN_NAME_CURRENT_WALLET_AMOUNT = "wallet_amount"
    }
}
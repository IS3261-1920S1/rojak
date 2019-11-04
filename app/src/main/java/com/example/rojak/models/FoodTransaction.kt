package com.example.rojak.models


class FoodTransaction(override val id: Int, override val walletAmount: Float,
                      override val timestamp: String, private val food_name : String, private val paid_amount : Float)
    : Transaction {

    override fun getHistoryDescription(): String {
        return "$$paid_amount was used to purchase a $food_name on $timestamp."
    }

    override fun getHistoryTitle(): String {
        return "-$$paid_amount"
    }
}
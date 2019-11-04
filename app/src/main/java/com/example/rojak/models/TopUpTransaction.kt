package com.example.rojak.models

class TopUpTransaction(override val id: Int, override val walletAmount: Float,
                       override val timestamp: String, val topup_amount : Float) : Transaction {

    override fun getHistoryDescription(): String {
        return "$$topup_amount was added to the wallet on $timestamp"
    }

    override fun getHistoryTitle(): String {
        return "+$$topup_amount"
    }
}
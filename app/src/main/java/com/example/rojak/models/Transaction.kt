package com.example.rojak.models


interface Transaction {
    val id : Int
    val walletAmount : Float
    val timestamp : String

    fun getHistoryDescription() : String
    fun getHistoryTitle(): String
}
package com.example.rojak.activities.wallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.rojak.R

class WalletActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        val dataSource = ArrayList<Pair<String, String>>()
        dataSource.add(Pair("Testing Amount", "Testing Description"))
        dataSource.add(Pair("Testing Amount", "Testing Description"))
        dataSource.add(Pair("Testing Amount", "Testing Description"))
        dataSource.add(Pair("Testing Amount", "Testing Description"))
        dataSource.add(Pair("Testing Amount", "Testing Description"))
        dataSource.add(Pair("Testing Amount", "Testing Description"))
        dataSource.add(Pair("Testing Amount", "Testing Description"))
        findViewById<ListView>(R.id.walletTransactions).adapter = WalletTransactionAdapter(this, dataSource)

    }
}

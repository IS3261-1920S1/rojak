package com.example.rojak.activities.wallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.rojak.R
import com.example.rojak.database.DatabaseHelper

class WalletActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        val dbHelper : DatabaseHelper = DatabaseHelper(this)
        findViewById<TextView>(R.id.currentWalletAmount).text = dbHelper.getCurrentWalletAmount().toString()

        val dataSource = ArrayList<Pair<String, String>>()
        dataSource.add(Pair("Testing Amount", "Testing Description"))
        dataSource.add(Pair("Testing Amount", "Testing Description"))
        dataSource.add(Pair("Testing Amount", "Testing Description"))
        dataSource.add(Pair("Testing Amount", "Testing Description"))
        dataSource.add(Pair("Testing Amount", "Testing Description"))
        dataSource.add(Pair("Testing Amount", "Testing Description"))
        dataSource.add(Pair("Testing Amount", "Testing Description"))
        findViewById<ListView>(R.id.walletTransactions).adapter = WalletTransactionAdapter(this, dataSource)
        findViewById<Button>(R.id.walletActivity_button_topup).setOnClickListener {
            val a = TopUpDialog()
            a.show(supportFragmentManager, "")
        }

    }
}

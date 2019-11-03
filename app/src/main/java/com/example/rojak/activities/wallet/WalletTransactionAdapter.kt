package com.example.rojak.activities.wallet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.rojak.R

class WalletTransactionAdapter(private val context : Context,
                               private val dataSource : ArrayList<Pair<String, String>>) : BaseAdapter() {

    private val inflater : LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.listview_wallet_transaction_row, p2, false)
        val rowItem = this.getItem(p0) as Pair<String, String>
        rowView.findViewById<TextView>(R.id.walletTransaction_transactionAmount).text = rowItem.first
        rowView.findViewById<TextView>(R.id.walletTransaction_transactionDesc).text = rowItem.second

        return rowView
    }

    override fun getItem(p0: Int): Any {
        return dataSource[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }
}
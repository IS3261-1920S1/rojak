package com.example.rojak.activities.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.rojak.R
import com.example.rojak.activities.wallet.WalletActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<Button>(R.id.button_goto_wallet).setOnClickListener {
            val walletIntent: Intent = Intent(this, WalletActivity::class.java)
            startActivity(walletIntent)
        }

    }
}

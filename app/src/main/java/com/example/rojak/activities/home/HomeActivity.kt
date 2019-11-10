package com.example.rojak.activities.home

import android.Manifest
import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.rojak.R
import com.example.rojak.activities.wallet.WalletActivity
import com.example.rojak.activities.qr.QRActivity
import com.example.rojak.database.DatabaseHelper
//import android.support.v7.app.AlertDialog;

class HomeActivity : AppCompatActivity() {
    var allPermissionsGrantedFlag : Int = 0
    var hasLaunchedAnotherActivity : Int = 0

    private val permissionList = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_FINE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (allPermissionsEnabled()) {
                allPermissionsGrantedFlag = 1
            } else {
                setupMultiplePermissions()
            }
        } else {
            allPermissionsGrantedFlag = 1
        }

        findViewById<Button>(R.id.button_goto_wallet).setOnClickListener {
            val walletIntent: Intent = Intent(this, WalletActivity::class.java)
            hasLaunchedAnotherActivity = 1
            startActivity(walletIntent)
        }

        findViewById<Button>(R.id.button_goto_qr).setOnClickListener {
            val walletIntent: Intent = Intent(this, QRActivity::class.java)
            hasLaunchedAnotherActivity = 1
            startActivity(walletIntent)
        }

        val rating = DatabaseHelper(this).getAvgWeeklyRating()
        val seeker = findViewById<SeekBar>(R.id.seekBar)
        seeker.isEnabled = false
        seeker.progress = (rating * 100).toInt()
        var suggest = ""
        if(rating > 0.70){
            suggest = "Your Healthiness Rating is reaching Highly Un-Healthy Levels!\n" +
                    "Please consider eating more...\n" +
                    "- Fruits\n" +
                    "- Vegetables\n" +
                    "- Healthier Food Options (Such as salads etc..)"
        }else if(rating > 0.4){
            suggest = "Your Healthiness Rating is in the Moderate Region\n" +
                    "Please watch your eating habits such that you do not reach the unhealthy Region."
        }else{
            suggest = "Keep up the healthy eating :)"
        }

        val suggestText = findViewById<TextView>(R.id.suggest)
        suggestText.text = suggest


    }

    override fun onResume() {
        super.onResume()
        if (hasLaunchedAnotherActivity != 0) {
            val i = getIntent()
            finish()
            startActivity(i)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun allPermissionsEnabled() : Boolean {
        return permissionList.none{ checkSelfPermission(it) !=
                PackageManager.PERMISSION_GRANTED}
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupMultiplePermissions(){
        val remainingPermissions = permissionList.filter {
            checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED
        }
        requestPermissions(remainingPermissions.toTypedArray(), 101)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissionList: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissionList, grantResults)
        if (requestCode == 101) {
            if (grantResults.any{it != PackageManager.PERMISSION_GRANTED}) {
                @TargetApi(Build.VERSION_CODES.M)
                if (permissionList.any{shouldShowRequestPermissionRationale(it)}){
                    println("not permitted")
                  //  AlertDialog.Builder(this)
                   //     .setMessage("Press Permissions to Decide Permission Again")
                    //    .setPositiveButton("Permissions"){dialog, which -> setupMultiplePermissions()}
                     //   .setNegativeButton("Cancel"){dialog, which->dialog.dismiss()}
                      //  .create()
                       // .show()
                }
            }
        }
    }
}

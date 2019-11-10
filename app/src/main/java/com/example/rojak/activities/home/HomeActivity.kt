package com.example.rojak.activities.home

import android.Manifest
import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import com.example.rojak.R
import com.example.rojak.activities.wallet.WalletActivity
import com.example.rojak.activities.qr.QRActivity
//import android.support.v7.app.AlertDialog;

class HomeActivity : AppCompatActivity() {
    var allPermissionsGrantedFlag : Int = 0

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
            startActivity(walletIntent)
        }

        findViewById<Button>(R.id.button_goto_qr).setOnClickListener {
            val walletIntent: Intent = Intent(this, QRActivity::class.java)
            startActivity(walletIntent)
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

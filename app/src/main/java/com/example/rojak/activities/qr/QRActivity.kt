package com.example.rojak.activities.qr

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.TextView
import com.example.rojak.R
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import org.json.JSONArray
import java.io.IOException
import java.net.URL
import com.example.rojak.database.DatabaseHelper
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class QRActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        val cameraView = findViewById<SurfaceView>(R.id.sv_CameraView)
        val tvCodeInfo = findViewById<TextView>(R.id.tv_QRDecoded)

        val barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.QR_CODE)
            .build()

        val cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(640, 480)
            .build()

        cameraView.holder.addCallback(object : SurfaceHolder.Callback{
            override fun surfaceCreated(holder: SurfaceHolder?) {
                try {
                    cameraSource.start(cameraView.holder)
                } catch (ie: IOException) {
                    Log.e("CAMERA SOURCE ", ie.message)
                }
            }

            override fun surfaceChanged(holder: SurfaceHolder?,
                                        format: Int, width: Int,
                                        height: Int) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                cameraSource.stop()
            }

        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode>{
            override fun release() {

            }
            override fun receiveDetections(detections: Detector.Detections<Barcode>?){
                val barcodes = detections?.detectedItems

                if (barcodes?.size() != 0) {
                //    val url = barcodes?.valueAt(0)?.displayValue
                    val code = barcodes?.valueAt(0)?.displayValue



                    val triple = DatabaseHelper(this@QRActivity).queryFood(code.toString())
                    val name = triple.first
                    val id = triple.second
                    val price = triple.third

                    val output = "Food Item: " + name  + "Price: " + price
                    tvCodeInfo.text = output
               //     barcodeDetector.release()


                    findViewById<Button>(R.id.confirmPurchase).setOnClickListener {
                        println("confirm")



                        val bal = DatabaseHelper(this@QRActivity).getCurrentWalletAmount()
                        if(price.toFloat() > bal){
                            val myToast = Toast.makeText(applicationContext,"Balance not enough. Balance: " + bal.toString(),Toast.LENGTH_SHORT)
                            myToast.show()
                        }else{

                            DatabaseHelper(this@QRActivity).addPurchase(price.toFloat(), id.toInt())

                            val myToast = Toast.makeText(applicationContext,"Purchase Success",Toast.LENGTH_SHORT)
                            myToast.show()
                        }


                    }

                    findViewById<Button>(R.id.cancelPurchase).setOnClickListener {
                        val i: Intent = Intent(this@QRActivity, QRActivity::class.java)
                        finish()
                        overridePendingTransition(0, 0)
                        startActivity(i)
                        overridePendingTransition(0, 0)
                    }



                }
            }
        })
    }
}
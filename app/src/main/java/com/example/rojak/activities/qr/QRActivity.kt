package com.example.rojak.activities.qr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.TextView
import com.example.rojak.R
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.io.IOException
import com.example.rojak.database.DatabaseHelper
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import org.json.JSONObject
import java.math.RoundingMode
import java.text.DecimalFormat


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
            .setAutoFocusEnabled(true)
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
                    val code = barcodes?.valueAt(0)?.displayValue


                    val rating : Float = DatabaseHelper(this@QRActivity).getAvgWeeklyRating()
                    val inflation : Float = 1 + rating - 0.5f


                    val foodData : JSONObject = DatabaseHelper(this@QRActivity).queryFood(code.toString())
                    val name = foodData.get("food_name") as String
                    val price = foodData.get("food_price") as String
                    val food_rating = foodData.get("food_rating") as Float

                    var new_price : Float

                    if (rating < 0.5) {
                        new_price = price.toFloat()
                    } else {
                        if (food_rating < 0.5) {
                            new_price = (1 - (rating - 0.5f)) * price.toFloat()
                        } else {
                            new_price = inflation * price.toFloat()
                        }
                    }

                    val df = DecimalFormat("#.##")
                    df.roundingMode = RoundingMode.CEILING
                    val rounded_price = df.format(new_price)


                    val output = "Food Item: " + name + "\n" + "Price: " + rounded_price
                    tvCodeInfo.text = output
               //     barcodeDetector.release()

                    findViewById<Button>(R.id.confirmPurchase).setOnClickListener {
                        val bal = DatabaseHelper(this@QRActivity).getCurrentWalletAmount()
                        if(price.toFloat() > bal){
                            val myToast = Toast.makeText(applicationContext,"Balance not enough. Balance: " + bal.toString(),Toast.LENGTH_SHORT)
                            myToast.show()
                        }else{
                            DatabaseHelper(this@QRActivity).addPurchase(rounded_price.toFloat(), code!!.toInt())

                            val myToast = Toast.makeText(applicationContext,"Purchase Success",Toast.LENGTH_SHORT)
                            myToast.show()
                            finish()
                        }
                    }

                    findViewById<Button>(R.id.cancelPurchase).setOnClickListener {
                        finish()
                    }



                }
            }
        })
    }
}

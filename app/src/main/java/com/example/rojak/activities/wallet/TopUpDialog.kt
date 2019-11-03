package com.example.rojak.activities.wallet

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.rojak.R
import com.example.rojak.database.DatabaseHelper

class TopUpDialog : DialogFragment () {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;
            val dialogView = inflater.inflate(R.layout.dialog_topup, null)

            builder.setMessage("Enter Top Up Amount")
                .setView(dialogView)
                .setPositiveButton("Top Up",
                    DialogInterface.OnClickListener { dialog, id ->
                        DatabaseHelper(context!!).topUpWallet(dialogView.findViewById<EditText>(R.id.dialog_topup_amount).text.toString().toFloat())
                        activity!!.finish()
                        activity!!.startActivity(activity!!.intent)
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(context, "Top Up Cancelled", Toast.LENGTH_LONG).show()
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
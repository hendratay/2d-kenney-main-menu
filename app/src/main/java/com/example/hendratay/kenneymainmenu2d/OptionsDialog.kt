package com.example.hendratay.kenneymainmenu2d

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

class OptionsDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity as MainActivity)
        builder.setView((activity as MainActivity).layoutInflater.inflate(R.layout.fragment_options, null))
                .setPositiveButton("Save") { _, _ ->

                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
        return builder.create()
    }

}
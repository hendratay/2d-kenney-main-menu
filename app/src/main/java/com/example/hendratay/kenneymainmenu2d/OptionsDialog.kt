package com.example.hendratay.kenneymainmenu2d

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.fragment_options.view.*

class OptionsDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity as MainActivity)
        val view = (activity as MainActivity).layoutInflater.inflate(R.layout.fragment_options, null)
        view.seekBar_music.progress = readSharedPreferences()
        builder.setView(view)
                .setPositiveButton("Save") { _, _ ->
                    writeSharedPreferences(view.seekBar_music.progress)
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
        return builder.create()
    }

    private fun readSharedPreferences(): Int {
        val sharedPref = activity?.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE) ?: return 0
        return sharedPref.getInt(getString(R.string.saved_music_volume), 50)
    }

    private fun writeSharedPreferences(progress: Int) {
        val sharedPref = activity?.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putInt(getString(R.string.saved_music_volume), progress)
            commit()
        }
        (activity as MainActivity).backgroundMusic?.setMusicVolume(readSharedPreferences() / 100f)
    }

}
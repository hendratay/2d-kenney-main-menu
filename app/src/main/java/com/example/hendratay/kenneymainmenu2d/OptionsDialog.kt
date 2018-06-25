package com.example.hendratay.kenneymainmenu2d

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.fragment_options.view.*

class OptionsDialog: DialogFragment() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        sharedPreferences = (activity as MainActivity).getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val builder = AlertDialog.Builder(activity as MainActivity)
        val view = (activity as MainActivity).layoutInflater.inflate(R.layout.fragment_options, null)
        view.seekBar_music.progress = readBackgroundMusicPref()
        view.seekBar_sound_effect.progress = readSoundEffectPref()
        builder.setView(view)
                .setPositiveButton("Save") { _, _ ->
                    writeBackgroundMusicPref(view.seekBar_music.progress)
                    writeSoundEffectPref(view.seekBar_sound_effect.progress)
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
        return builder.create()
    }

    private fun readBackgroundMusicPref(): Int {
        return sharedPreferences.getInt(getString(R.string.saved_music_volume), 50)
    }

    private fun readSoundEffectPref(): Int {
        return sharedPreferences.getInt(getString(R.string.saved_sound_effect), 50)
    }

    private fun writeBackgroundMusicPref(progress: Int) {
        with(sharedPreferences.edit()) {
            putInt(getString(R.string.saved_music_volume), progress)
            commit()
        }
        (activity as MainActivity).backgroundMusic?.setMusicVolume(readBackgroundMusicPref() / 100f)
    }

    private fun writeSoundEffectPref(progress: Int) {
        with(sharedPreferences.edit()) {
            putInt(getString(R.string.saved_sound_effect), progress)
            commit()
        }
        (activity as MainActivity).setSoundEffectVolume(readSoundEffectPref())
    }

}
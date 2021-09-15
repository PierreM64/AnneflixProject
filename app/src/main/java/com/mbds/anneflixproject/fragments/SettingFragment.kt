package com.mbds.anneflixproject.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButtonToggleGroup
import com.mbds.anneflixproject.NavigationListener
import com.mbds.anneflixproject.R

class SettingFragment : Fragment() {

    lateinit var btg_theme: MaterialButtonToggleGroup

    private val TAG = "MainActivity"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.setting_fragment, container, false)
        //change toolbar title by title fragment
        (activity as? NavigationListener)?.let {
            it.updateTitle(R.string.setting)
        }

        btg_theme = view.findViewById(R.id.btg_theme)
        //s'il y a un nouveau boutton checked dans le MaterialButtonToggleGroup
        btg_theme.addOnButtonCheckedListener { _, selectedBtnId, isChecked ->
            if (isChecked) {
                val theme = when (selectedBtnId) {
                    R.id.btnDefault -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    R.id.btnDark -> AppCompatDelegate.MODE_NIGHT_YES
                    else -> AppCompatDelegate.MODE_NIGHT_NO
                }
                val toastmsg = when (selectedBtnId) {
                    R.id.btnDefault -> "Default Mode"
                    R.id.btnDark -> "Dark Mode"
                    else -> "Light Mode"
                }
                Log.d(TAG, "isChecked:$isChecked theme:$theme")
                AppCompatDelegate.setDefaultNightMode(theme)
                Toast.makeText(activity, "$toastmsg", Toast.LENGTH_SHORT).show()
            }

        }

        return view
    }

}
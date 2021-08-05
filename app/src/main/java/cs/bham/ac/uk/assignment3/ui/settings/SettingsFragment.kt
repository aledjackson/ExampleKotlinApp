package cs.bham.ac.uk.assignment3.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import cs.bham.ac.uk.assignment3.R


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        val listpref: ListPreference? = findPreference("meal")
        val orderpref: ListPreference? = findPreference("order")
        orderpref?.setSummary(orderpref.entry)

        orderpref?.setOnPreferenceChangeListener{ preference, newValue ->
            val summary = when(newValue as String){
                "asc"  -> "ascending"
                "desc" -> "descending"
                else   -> "unexpected"
            }

            preference.setSummary(summary)
            true
        }

        listpref?.setSummary(listpref.value)
        listpref?.setOnPreferenceChangeListener { preference, newValue ->
            preference.setSummary(newValue as String)
            true
        }
    }
}


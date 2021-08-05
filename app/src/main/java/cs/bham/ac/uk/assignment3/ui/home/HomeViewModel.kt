package cs.bham.ac.uk.assignment3.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.R.string
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class HomeViewModel : ViewModel() {

    val _meals = MutableLiveData<JSONArray>().apply{
        value = JSONArray()
    }

    val meals: LiveData<JSONArray> = _meals


    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }


    val text: LiveData<String> = _text


    var client = OkHttpClient()



    fun makeAPICalls(context: Context?){
        getMeals(context)
    }

    fun getMeals(context: Context?) {
//        I fucking hate android

        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val mealPref = preferences?.getString("meal","")
        val orderPref = preferences?.getString("order","")


        val url = "https://www.sjjg.uk/eat/food-items?prefer=${mealPref}&ordering=${orderPref}"
        println(url)
        val request = Request.Builder()
                .url(url)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e)
            }
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val parsed = JSONArray(body)
                _meals.postValue(parsed)
            }
        })
    }

    fun itemClicked(position: Int){
        System.out.println(position)
    }


}
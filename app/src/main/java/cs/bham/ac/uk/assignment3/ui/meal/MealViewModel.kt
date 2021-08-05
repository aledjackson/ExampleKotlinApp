package cs.bham.ac.uk.assignment3.ui.meal

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MealViewModel : ViewModel() {

    var name: String = ""
    var foodId: String = ""
    var client = OkHttpClient()



    val _description = MutableLiveData<String>().apply{ value = "Loading"}
    val _ingredients = MutableLiveData<String>()
    val _steps = MutableLiveData<String>()

    val description: LiveData<String> = _description
    val ingredients: LiveData<String> = _ingredients
    val steps: LiveData<String> = _steps


//    val _description = MutableLiveData<String>().apply{ value = "Loading"}

    fun makeAPICalls(context: Context?){
        getDescription(context)
    }

    fun getDescription(context: Context?) {

        val url = "https://www.sjjg.uk/eat/recipe-details/${foodId}"
        println(url)
        val request = Request.Builder()
                .url(url)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e)
            }
            override fun onResponse(call: Call, response: Response) {
                val parsed = JSONObject(response.body?.string() ?: "")
                _description.postValue(parsed.get("description") as String)
                _ingredients.postValue(arrayToString(parsed.get("ingredients") as JSONArray))
                _steps.postValue(arrayToString(parsed.get("steps") as JSONArray))
            }
        })
    }

    fun arrayToString(ingredients: JSONArray):String{
        var x = 0
        var parsed = ""
        while(x < ingredients.length()){
            val ingredient = "•\t" + ingredients.get(x) as String
            parsed = parsed + ingredient + "\n"
            x++
        }
        return parsed
    }

}
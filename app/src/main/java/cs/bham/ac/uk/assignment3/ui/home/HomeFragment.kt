package cs.bham.ac.uk.assignment3.ui.home

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cs.bham.ac.uk.assignment3.R
import cs.bham.ac.uk.assignment3.ui.meal.MealFragment
import org.json.JSONArray
//import cs.bham.ac.uk.assignment3.R as R
import okhttp3.*
import org.json.JSONObject


class HomeFragment : Fragment(), MyAdapter.OnItemClickListener {


    private val viewModel: HomeViewModel by viewModels()
    var client = OkHttpClient()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)


        val myDataset = viewModel.meals

        val viewManager = LinearLayoutManager(context)


//        this could be refactored to take a method instead of an object
        val viewAdapter = MyAdapter(myDataset.value ?: JSONArray(), this)

        var recyclerView = root.findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }


//        this will break if viewAdapter is not given a pointer to myDataset
        viewModel._meals.observe(this,Observer{
            viewAdapter.myDataset = it
            viewAdapter.notifyDataSetChanged()
        })


        viewModel.makeAPICalls(context)

        return root

    }


    override fun onItemClick(position: Int) {
        viewModel.itemClicked(position)

//        navigate to new view
        val navigator = Navigation.findNavController(activity as Activity, R.id.nav_host_fragment)
        val meals = viewModel.meals.value
//        this should never be null anyway
        val meal: JSONObject? = meals?.get(position) as JSONObject

        val name = meal?.get("name") as String ?: "UNKNOWN"
        val id = (meal.get("id") as Int).toString() ?: "UNKNOWN"

        var arguments = MealFragment.bundleArguments(name,id)
        navigator.navigate(R.id.navigation_meal, arguments)

    }




}
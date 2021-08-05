package cs.bham.ac.uk.assignment3.ui.meal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

import cs.bham.ac.uk.assignment3.R


class MealFragment : Fragment() {

    private val viewModel: MealViewModel by viewModels()

    private var description: String? = null
    private var foodId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.name = it.getString(ARG_NAME) ?: "UNKNOWN"
            viewModel.foodId = it.getString(ARG_ID) ?: "UNKNOWN"
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_meal, container, false)
        viewModel.makeAPICalls(context)
        val mealName = root.findViewById<TextView>(R.id.meal_name)
        mealName.text = viewModel.name
        val description = root.findViewById<TextView>(R.id.meal_description)
        val ingredients = root.findViewById<TextView>(R.id.meal_ingredients)
        val steps = root.findViewById<TextView>(R.id.meal_steps)


        viewModel._description.observe(this, Observer {
            description.text = it
        })

        viewModel._ingredients.observe(this, Observer {
            ingredients.text = it
        })

        viewModel._steps.observe(this, Observer {
            steps.text = it
        })
        return root

    }

    companion object {
        private val ARG_NAME = "name"
        private val ARG_ID = "id"

        fun bundleArguments(name: String, id: String): Bundle{
            val arguments = Bundle().apply {
                putString(ARG_NAME, name)
                putString(ARG_ID, id)
            }
            return arguments
        }
    }


}

package cs.bham.ac.uk.assignment3.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cs.bham.ac.uk.assignment3.R
import org.json.JSONArray
import org.json.JSONObject

class MyAdapter(var myDataset: JSONArray, private val itemListener: OnItemClickListener) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val textView: TextView, val listener: OnItemClickListener) : RecyclerView.ViewHolder(textView), View.OnClickListener {
        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onItemClick(adapterPosition)
        }

    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.MyViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.my_text_view, parent, false) as TextView

        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(textView, itemListener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val record = myDataset.get(position) as JSONObject
        holder.textView.text = record.get("name") as String
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.length()

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}
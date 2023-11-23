package com.example.cupcake.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cupcake.R
import com.example.cupcake.model.OrderViewModel

class FlavorAdapter(
    private val orderViewModel: OrderViewModel
) : RecyclerView.Adapter<FlavorAdapter.FlavorViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.
    class FlavorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.label)
        val quantityTextView: TextView = view.findViewById(R.id.quantityTextView)
        val minusButton: Button = view.findViewById(R.id.minusButton)
        val plusButton: Button = view.findViewById(R.id.plusButton)

    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlavorViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_flavor_number, parent, false)

        return FlavorViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: FlavorViewHolder, position: Int) {
        val item = orderViewModel.getFlavorQuantityList()[position]
        holder.textView.text = item.name
        holder.quantityTextView.text = item.quantity.value.toString()
        holder.plusButton.setOnClickListener() {
            if (orderViewModel.remainingQuantity.value!! > 0) {
                orderViewModel.setRemainingQuantity(orderViewModel.remainingQuantity.value!! - 1)
                item.setQuantity(item.quantity.value!! + 1)
                holder.quantityTextView.text = item.quantity.value.toString()
            }

        }
        holder.minusButton.setOnClickListener {
            if (orderViewModel.remainingQuantity.value!! <= 6 && item.quantity.value!! > 0) {
                orderViewModel.setRemainingQuantity(orderViewModel.remainingQuantity.value!! + 1)
                item.setQuantity(item.quantity.value!! - 1)
                holder.quantityTextView.text = item.quantity.value.toString()
            }

        }
    }

        /**
         * Return the size of your dataset (invoked by the layout manager)
         */
        override fun getItemCount(): Int = orderViewModel.getFlavorQuantityList().size
    }
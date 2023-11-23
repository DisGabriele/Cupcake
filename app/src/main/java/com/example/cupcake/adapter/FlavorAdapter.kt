package com.example.cupcake.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.devs.vectorchildfinder.VectorChildFinder
import com.example.cupcake.R
import com.example.cupcake.model.OrderViewModel


class FlavorAdapter(
    private val context: Context,
    private val orderViewModel: OrderViewModel
) : RecyclerView.Adapter<FlavorAdapter.FlavorViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.
    class FlavorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.label)
        val imageView: ImageView = view.findViewById(R.id.imageView)
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
        val item = orderViewModel.dataset[position]
        val vector =
            VectorChildFinder(context, R.drawable.ic_cupcake, holder.imageView)

        item.getColorMap().forEach { flavor ->
            vector.findPathByName(flavor.key).fillColor = flavor.value
        }
        holder.textView.text = item.name
        holder.quantityTextView.text = item.quantity.value.toString()
        if (position != 2) {
            holder.plusButton.setOnClickListener {
                if (orderViewModel.remainingQuantity.value!! > 0) {
                    orderViewModel.setRemainingQuantity(orderViewModel.remainingQuantity.value!! - 1)
                    item.setQuantity(item.quantity.value!! + 1)
                    holder.quantityTextView.text = item.quantity.value.toString()
                }
            }
        } else {
            holder.plusButton.setOnClickListener {
                if (orderViewModel.remainingQuantity.value!! > 0) {
                    orderViewModel.setRemainingQuantity(orderViewModel.remainingQuantity.value!! - 1)
                    item.setQuantity(item.quantity.value!! + 1)
                    holder.quantityTextView.text = item.quantity.value.toString()
                    if (item.quantity.value!! == 1) {
                        Toast.makeText(
                            context,
                            context.resources.getString(R.string.special_flavor_warning),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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
    }

        /**
         * Return the size of your dataset (invoked by the layout manager)
         */
        override fun getItemCount(): Int = orderViewModel.dataset.size
    }
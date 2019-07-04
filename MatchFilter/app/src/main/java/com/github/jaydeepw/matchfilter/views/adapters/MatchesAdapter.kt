package com.github.jaydeepw.matchfilter.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.jaydeepw.matchfilter.R
import com.github.jaydeepw.matchfilter.models.entities.Match
import kotlinx.android.synthetic.main.list_item.view.*

class MatchesAdapter(val items: MutableList<Match>?, val context: Context) : RecyclerView.Adapter<ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHoldder =  ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
        return viewHoldder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val match = items?.get(position)
        holder.itemView.tag = match
        holder.title.text = match?.display_name?.capitalize()
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items?.size!!
    }

    fun updateAll(newItems: List<Match>) {
        items?.clear()
        items?.addAll(newItems)
        notifyDataSetChanged()
    }
}

class ViewHolder : RecyclerView.ViewHolder {

    var title : TextView

    constructor(view: View) : super(view) {
        // Holds the TextView that will add each animal to
        title = view.textview_name
    }
}
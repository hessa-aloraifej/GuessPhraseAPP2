package com.example.guessphraseapp2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.guessphraseapp2.R
import kotlinx.android.synthetic.main.item_row.view.*

class RecyclerViewAdapter(private val guesses:ArrayList<String>):RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {
    class ItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val guess=guesses[position]
        holder.itemView.apply {
            textView3.text=guess
        }
    }

    override fun getItemCount() = guesses.size

}
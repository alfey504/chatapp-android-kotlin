package com.aab.bot.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aab.bot.R

class ChatViewAdapter(private var data: MutableList<String>): RecyclerView.Adapter<ChatViewAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textView: TextView

        init {
            textView = view.findViewById<TextView>(R.id.chat_receive_item)
        }
    }

    fun setData(data: MutableList<String>){
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.visibility = View.VISIBLE
        holder.textView.text = data[position]
    }

    override fun getItemCount(): Int {
        return data.count()
    }


}
package com.aab.bot.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aab.bot.R
import com.aab.bot.data_models.Messages

class ChatViewAdapter(private var data: MutableList<Messages>): RecyclerView.Adapter<ChatViewAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val message: TextView
        val username: TextView

        init {
            message = view.findViewById<TextView>(R.id.chat_receive_item)
            username = view.findViewById<TextView>(R.id.chat_list_item_username)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.message.visibility = View.VISIBLE
        holder.message.text = data[position].message
        holder.username.visibility = View.VISIBLE
        holder.username.text = data[position].userName
    }

    override fun getItemCount(): Int {
        return data.count()
    }


}
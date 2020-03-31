/**
 * FileName: MySimpleAdapter
 * Author: Administrator
 * Date: 2020/1/3 15:21
 * Description: Simple Adapter, only one TextView.
 */
package com.demos.kotlin.adaper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.demos.kotlin.R
import com.demos.kotlin.adaper.MySimpleAdapter.MyViewHolder

class MySimpleAdapter(
        var list: List<String>,
        val context: Context
) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(
                R.layout.item_rcview1, parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onItemClickListener.onItemClick(position) }
        holder.itemView.findViewById<TextView>(R.id.text_item_rcview)?.text = list.get(position)
    }

    lateinit var onItemClickListener: MyOnItemOnClickListener

    override fun getItemCount(): Int {
        return list.size
    }


    fun setMyOnItemClickListener(listener: MyOnItemOnClickListener) {
        onItemClickListener = listener
    }

    interface MyOnItemOnClickListener {
        fun onItemClick(position: Int)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}


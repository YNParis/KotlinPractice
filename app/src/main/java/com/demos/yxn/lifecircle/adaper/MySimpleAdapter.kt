/**
 * FileName: MySimpleAdapter
 * Author: Administrator
 * Date: 2020/1/3 15:21
 * Description: Simple Adapter, only one TextView.
 */
package com.demos.yxn.lifecircle.adaper

import android.content.Context
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.demos.yxn.lifecircle.R
import com.demos.yxn.lifecircle.adaper.MySimpleAdapter.MyViewHolder

class MySimpleAdapter(
  var list: List<String>,
  val context: Context
) : Adapter<MyViewHolder>() {

  lateinit var onItemClickListener: MyOnItemOnClickListener

  override fun onCreateViewHolder(
    parent: ViewGroup?,
    viewType: Int
  ): MyViewHolder {
    val itemView = LayoutInflater.from(context).inflate(
        R.layout.item_rcview1, parent, false
    )
    return MyViewHolder(itemView)
  }

  override fun getItemCount(): Int {
    return list.size
  }

  override fun onBindViewHolder(
    holder: MyViewHolder?,
    position: Int
  ) {
    holder?.itemView?.setOnClickListener { onItemClickListener.onItemClick(position) }
    holder?.itemView?.findViewById<TextView>(R.id.text_item_rcview)?.text = list[position]
  }

  fun setMyOnItemClickListener(listener: MyOnItemOnClickListener) {
    onItemClickListener = listener
  }

  interface MyOnItemOnClickListener {
    fun onItemClick(position: Int)
  }

  class MyViewHolder(itemView: View) : ViewHolder(itemView)

}


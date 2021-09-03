package com.ongoshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.clickListeners.CategoryClick
import com.ongoshop.pojo.CategoryListResponse
import com.ongoshop.pojo.MyProductListingResponse
import java.util.*

class CategoryAdapter(var context: Context, var list: ArrayList<CategoryListResponse.Body>,
                      var categoryClick: CategoryClick) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    var inflater: LayoutInflater
    var templist: ArrayList<CategoryListResponse.Body>

    init {
        inflater = LayoutInflater.from(context)
        this.templist = list
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val v = inflater.inflate(R.layout.res_shopcategory, parent, false)
        return CategoryViewHolder(v)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.tvName.text = list[position].name
        holder.itemView.setOnClickListener { categoryClick.categoryClickk(position, list[position].id.toString(),
                list[position].name, list.size) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun filter(charText: String, nobooking: TextView) {
        var charText = charText
        charText = charText.toLowerCase()
        val nList: MutableList<CategoryListResponse.Body?> =
                ArrayList<CategoryListResponse.Body?>()
        if (charText.length == 0) {
            nList.addAll(templist)
        } else {
            for (wp in templist) {
                if (wp!!.name!!.toLowerCase()
                                .contains(charText.toLowerCase())) {
                    nList.add(wp)
                }
            }
        }
        list = nList as ArrayList<CategoryListResponse.Body>
        notifyDataSetChanged()


        if (templist.isEmpty()) {
            nobooking.setVisibility(View.VISIBLE)
        } else {
            nobooking.setVisibility(View.GONE)
        }
    }


    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.findViewById(R.id.tvName)
    }


}
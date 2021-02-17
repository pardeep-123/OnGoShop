package com.ongoshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.activities.SubCategoriesActivity
import com.ongoshop.models.CategoryClick
import com.ongoshop.pojo.CategoryListResponse
import java.util.*

class SubCategoryAdapter(internal var context: Context, internal var list: ArrayList<CategoryListResponse.Body>,
                         internal var onClickListener: CategoryClick) : RecyclerView.Adapter<SubCategoryAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.res_shopcategory, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bindItems(list[position])
      /*
        holder.itemView.setOnClickListener {

        }*/
    }

    override fun getItemCount(): Int {
        return list.size
    }

    init {
        inflater = LayoutInflater.from(context)
    }

    inner class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(categoryList: CategoryListResponse.Body) {
            val tvName = itemView.findViewById(R.id.tvName) as TextView
            tvName.text = categoryList.name

        }


        init {
            itemView.setOnClickListener {
                onClickListener.categoryClickk( list.get(adapterPosition).id.toString(), list.get(adapterPosition).name.toString(), list.size)
              //  selectedpos =adapterPosition
                notifyDataSetChanged()
            }

        }
    }

}
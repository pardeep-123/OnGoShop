package com.ongoshop.adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.activities.AddShopActivity
import com.ongoshop.pojo.CategoryListResponse
import com.ongoshop.utils.helperclasses.CategoryTypesClicklisetener


class CategoryTypesAddShopAdapter(
    val context: Context?,
    internal var getTypesOfCategoryList: ArrayList<CategoryListResponse.Body>,
    internal var addShopActivity: AddShopActivity, internal var categoryTypesClicklisetener: CategoryTypesClicklisetener
) : RecyclerView.Adapter<CategoryTypesAddShopAdapter.CategoryTypesHomeHolder>() {
    override fun onBindViewHolder(holder: CategoryTypesHomeHolder, position: Int) {
        holder.bindItems(getTypesOfCategoryList[position])
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryTypesHomeHolder {
        return CategoryTypesHomeHolder(
            LayoutInflater.from(context).inflate(R.layout.category_types_card, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return getTypesOfCategoryList.size
    }

    inner class CategoryTypesHomeHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to

        fun bindItems(getTypesOfCategoryList: CategoryListResponse.Body) {
            val tvCategoryTypes = itemView.findViewById(R.id.tv_category_types) as TextView

            tvCategoryTypes.setText(getTypesOfCategoryList.name)

            tvCategoryTypes.setOnClickListener {
                categoryTypesClicklisetener.categoryTypeclick(adapterPosition, getTypesOfCategoryList.id.toString(), getTypesOfCategoryList.name)
            }
        }

        init {
            itemView.setOnClickListener {

            }
        }
    }
}


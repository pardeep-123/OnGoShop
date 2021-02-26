package com.ongoshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ongoshop.R
import com.ongoshop.activities.ProductActivity
import com.ongoshop.activities.ProductDetailActivity
import com.ongoshop.clickListeners.CategoryClick
import com.ongoshop.clickListeners.ProductClick
import com.ongoshop.pojo.ProductListingResponse

class ProductAdapter(internal var context: Context, internal var productList: ArrayList<ProductListingResponse.Body?>,
                     internal var onClickListener: ProductClick)
    : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
  

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
                LayoutInflater.from(context).inflate(R.layout.list_products, parent, false)
        )
       
       /* val v = inflater.inflate(R.layout.list_products, parent, false)
        rl_list = v.findViewById(R.id.rl_list)
        rl_list!!.setOnClickListener(View.OnClickListener { context.startActivity(Intent(context, ProductDetailActivity::class.java)) })
        return ProductViewHolder(v)*/
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindItems(productList[position])
        
    }
    
    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(view: View?) : RecyclerView.ViewHolder(view!!){

        val ivProduct = itemView.findViewById(R.id.iv_product) as ImageView
        val tvProductName = itemView.findViewById(R.id.tv_product_name) as TextView
        val tvProductWeight = itemView.findViewById(R.id.tv_product_weight) as TextView
        val tvProductDesc = itemView.findViewById(R.id.tv_product_desc) as TextView
        val tvProductPrice = itemView.findViewById(R.id.tv_product_price) as TextView
        val rlProductlist = itemView.findViewById(R.id.rl_list) as RelativeLayout
        val btnNotAvailable = itemView.findViewById(R.id.btn_not_available) as Button
        val btnAvailable = itemView.findViewById(R.id.btn_available) as Button

        fun bindItems(productListing: ProductListingResponse.Body?) {
            tvProductName.setText(productListing!!.name)
            if (productListing.weightUnit!!.equals(0)){
                tvProductWeight.setText(productListing!!.weight+" "+ context.getString(R.string.kilograms))
            }else{
                tvProductWeight.setText(productListing!!.weight+" "+ context.getString(R.string.pounds))
            }
            tvProductDesc.setText(productListing!!.description)
            tvProductPrice.setText("$"+productListing!!.mrp)

            if (productListing.isAvailable!!.equals(1)){
                btnAvailable.setBackgroundResource(R.drawable.btn)
                btnNotAvailable.setBackgroundResource(R.drawable.na_button)
            }else{
                btnNotAvailable.setBackgroundResource(R.drawable.btn)
                btnAvailable.setBackgroundResource(R.drawable.na_button)
            }

            Glide.with(context).load(productListing.image).error(R.mipmap.no_image_placeholder).into(ivProduct)

            rlProductlist.setOnClickListener {
                var intent= Intent(context, ProductDetailActivity::class.java)
                intent.putExtra("productId", productList.get(adapterPosition)!!.id.toString())
                intent.putExtra("productImage", productList.get(adapterPosition)!!.image)
                intent.putExtra("productName", productList.get(adapterPosition)!!.name)
                intent.putExtra("barcode", productList.get(adapterPosition)!!.barcode)
                intent.putExtra("countryOfOrigin", productList.get(adapterPosition)!!.countryOfOrigin)
                intent.putExtra("mrp", productList.get(adapterPosition)!!.mrp)
                intent.putExtra("brand", productList.get(adapterPosition)!!.brandName)
                intent.putExtra("weight", tvProductWeight.text.toString())
                intent.putExtra("description", productList.get(adapterPosition)!!.description)

                context.startActivity(intent)
            }

            btnAvailable.setOnClickListener {
                btnAvailable.setBackgroundResource(R.drawable.btn)
                btnNotAvailable.setBackgroundResource(R.drawable.na_button)
                onClickListener.productClickk(adapterPosition, productListing.id.toString(), "1")
            }

            btnNotAvailable.setOnClickListener {
                onClickListener.productClickk(adapterPosition, productListing.id.toString(), "0")
                btnNotAvailable.setBackgroundResource(R.drawable.btn)
                btnAvailable.setBackgroundResource(R.drawable.na_button)
            }

        }

    }

}
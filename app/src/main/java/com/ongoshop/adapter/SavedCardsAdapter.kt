package com.ongoshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.activities.AddCardActivity
import com.ongoshop.activities.PaymentActivity
import com.ongoshop.pojo.GetAddedCardListResponse
import java.util.ArrayList


class SavedCardsAdapter(
        val mContext: Context?,
        internal var savedCardList: ArrayList<GetAddedCardListResponse.Body>,
        internal var paymentActivity: PaymentActivity
) : RecyclerView.Adapter<SavedCardsAdapter.SavedCardsHolder>() {

    var selectedpos = -1

    override fun onBindViewHolder(holder: SavedCardsHolder, position: Int) {
        holder.bindItems(savedCardList[position], position)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedCardsHolder {
        return SavedCardsHolder(LayoutInflater.from(mContext).inflate(R.layout.saved_payment_card, parent, false))
    }

    override fun getItemCount(): Int {
        return savedCardList.size
    }


    inner class SavedCardsHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(savedCardList: GetAddedCardListResponse.Body, position: Int) {
            val tvCardNumber = itemView.findViewById(R.id.tv_card_number) as TextView
            val tvCardName = itemView.findViewById(R.id.tv_name) as TextView
            val tvEditCard = itemView.findViewById(R.id.tv_edit_card) as TextView
            val tvCardExpiryDate = itemView.findViewById(R.id.card_expiry_date) as TextView
            val ivDeleteCard = itemView.findViewById(R.id.iv_delete_card) as ImageView
            val ivSelectCard = itemView.findViewById(R.id.iv_select_card) as ImageView
            val tvSelectCard = itemView.findViewById(R.id.tv_select_card) as TextView
            //  val radioBtn = itemView.findViewById(R.id.radio_btn) as RadioButton


            tvCardNumber.text = savedCardList.cardNumber
            tvCardName.text = savedCardList.name
            tvCardExpiryDate.text = savedCardList.month.toString() + "/" + savedCardList.year.toString()

            ivDeleteCard.setOnClickListener {
                paymentActivity.deleteAPIMethod(position, savedCardList.id.toString())
            }

            tvEditCard.setOnClickListener {
                var intent =  Intent(mContext, AddCardActivity::class.java)
                intent.putExtra("id", savedCardList.id.toString())
                intent.putExtra("cardNumber", savedCardList.cardNumber)
                intent.putExtra("name", savedCardList.name)
                intent.putExtra("month", savedCardList.month.toString())
                intent.putExtra("year", savedCardList.year.toString())
                intent.putExtra("cardType", savedCardList.cardType.toString())


                mContext!!.startActivity(intent)
            }

            tvSelectCard.setOnClickListener {
                selectedpos = position
                if (selectedpos == position) {
                    ivSelectCard.visibility = View.VISIBLE
                    tvSelectCard.visibility = View.GONE
                    paymentActivity.cardIdMethod(position, savedCardList.id.toString())
                    notifyDataSetChanged()

                } else {
                    ivSelectCard.visibility = View.GONE
                    tvSelectCard.visibility = View.VISIBLE
                    notifyDataSetChanged()
                }
            }

        }


        init {
            itemView.setOnClickListener {

            }
        }
    }
}
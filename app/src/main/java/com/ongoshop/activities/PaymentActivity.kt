package com.ongoshop.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.adapter.SavedCardsAdapter
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.DeleteCardResponse
import com.ongoshop.pojo.GetAddedCardListResponse
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_payment.*
import java.util.HashMap

class PaymentActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    var mContext: PaymentActivity? = null
    var btnPay: Button? = null
    var dialog: Dialog? = null
    var tvAddCard: TextView? = null

    lateinit var savedCardsAdapter: SavedCardsAdapter
    var savedCardList: ArrayList<GetAddedCardListResponse.Body> = ArrayList()
    var pos = 0
    private var bookingId = ""
    private var cardId = ""
    private var from = ""
    private val viewModel: HomeViewModel
            by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }


    override fun getContentId(): Int {
        return R.layout.activity_payment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        btnPay = findViewById(R.id.btnPay)
        tvAddCard = findViewById(R.id.tv_add_card)

        ivBack.setOnClickListener(mContext)
        btnPay!!.setOnClickListener(mContext)
        tvAddCard!!.setOnClickListener(mContext)

    }

    fun cardIdMethod(position: Int, cardid: String?) {
        cardId = cardid!!
        if (mValidationClass!!.isNetworkConnected()) {
            if (cardId.equals("") || cardId == null) {
                CommonMethods.AlertErrorMessage(mContext, "Please select/add card")
            } else {

            }
        } else {
            CommonMethods.failureMethod(mContext, getString(R.string.no_internet))
        }

    }

    override fun onResume() {
        super.onResume()
        if (!mValidationClass!!.isNetworkConnected()) {
            showAlerterRed(resources.getString(R.string.no_internet))
        } else {
            viewModel.allCardsAPI(this, true)
            viewModel.mResponse.observe(this, this)
        }

    }


    fun setSavedCardAdapter(savedCardList: ArrayList<GetAddedCardListResponse.Body>?) {
        savedCardsAdapter = SavedCardsAdapter(mContext, savedCardList!!, this)
        rv_payment_card.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        rv_payment_card.adapter = savedCardsAdapter
    }

    fun deleteAPIMethod(position: Int, id: String?) {
        pos = position
        if (!mValidationClass!!.isNetworkConnected()) {
            showAlerterRed(resources.getString(R.string.no_internet))
        } else {
           /* val map = HashMap<String, String>()
            map.put("id", id!!)*/
            viewModel.deleteCardAPI(mContext!!, true, id!!)
        }
    }


    private fun showDialog() {
        dialog = Dialog(mContext!!)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.setContentView(R.layout.alert_payment)
        dialog!!.setCancelable(true)
        val btnDone = dialog!!.findViewById<Button>(R.id.btnDone)
        btnDone.setOnClickListener {
            mContext!!.startActivity(Intent(mContext, HomeActivity::class.java))
            dialog!!.dismiss()
        }
        dialog!!.show()
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.ivBack ->{
                onLeftIconClick()
            }
         R.id.btnPay ->{
             showDialog()
            }
         R.id.tv_add_card ->{
             val intent = Intent(mContext, AddCardActivity::class.java)
             startActivity(intent)
            }
        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is GetAddedCardListResponse) {
                    val getAddedCardListResponse: GetAddedCardListResponse = it.data
                    if (getAddedCardListResponse.getCode() == Constants.success_code) {
                        savedCardList.clear()
                        savedCardList.addAll(getAddedCardListResponse!!.getBody()!!)

                        if (getAddedCardListResponse.getBody()!!.size == 0) {
                            no_payment_card.visibility = View.VISIBLE
                            rv_payment_card.visibility = View.GONE
                        } else {
                            no_payment_card.visibility = View.GONE
                            rv_payment_card.visibility = View.VISIBLE
                            setSavedCardAdapter(savedCardList)
                        }
                    }

                }

                if (it.data is DeleteCardResponse) {
                    val deleteCardResponse: DeleteCardResponse = it.data
                    if (deleteCardResponse.getCode()!!.equals(Constants.success_code)) {
                        showSuccessToast(mContext!!, deleteCardResponse!!.getMessage()!!)

                        savedCardList.removeAt(pos)
                        savedCardsAdapter.notifyDataSetChanged()
                        Log.e("deleteListSize", savedCardList.size.toString())

                        if (savedCardList!!.size == 0) {
                            no_payment_card.visibility = View.VISIBLE
                            rv_payment_card.visibility = View.GONE

                        } else {
                            no_payment_card.visibility = View.GONE
                            rv_payment_card.visibility = View.VISIBLE
                        }
                    } else {
                        CommonMethods.AlertErrorMessage(mContext, deleteCardResponse.getMessage())
                    }

                }

            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    showAlerterRed(it.data as String)
                } else {
                    showAlerterRed(it.error!!.toString())
                }
            }
            it.status == Status.LOADING -> {

            }
        }

    }

}
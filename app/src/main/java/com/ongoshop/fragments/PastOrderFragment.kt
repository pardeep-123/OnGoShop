package com.ongoshop.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.adapter.PastOrdersAdapter
import com.ongoshop.base.BaseFragment
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.PastOrderListResponse
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_past_order.*
import java.util.*
import kotlin.collections.ArrayList

class PastOrderFragment : BaseFragment(), Observer<RestObservable> {
    var mContext: Context? = null
    var v: View? = null
    var rvPastOrders: RecyclerView? = null
    var tvNoPastOrders: TextView? = null
    var pastOrderAdapter: PastOrdersAdapter? = null
    private val viewModel: HomeViewModel
            by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_past_order, container, false)
        mContext = activity
        rvPastOrders = v!!.findViewById(R.id.rv_past_orders)
        tvNoPastOrders = v!!.findViewById(R.id.tv_no_past_order)
        pastOrdersAPI()

        return v
    }

     fun pastOrdersAPI() {
        // val value = requireArguments().getString("pastOrders")
       //  if (requireActivity().intent.extras != null) {
             if (!MyApplication.hasNetwork()) {
                 showAlerterRed(resources.getString(R.string.no_internet))
             } else {
                 val map = HashMap<String, String>()
                 map.put("id", "")

                 viewModel.pastOrderListApi(requireActivity(), true, map)
                 viewModel.mResponse.observe(requireActivity(), this)
             }
      //   }

     }

    fun setPastOrdersAdapter(pastOrderListResponse: ArrayList<PastOrderListResponse.Body?>?) {
        pastOrderAdapter = PastOrdersAdapter(mContext!!, pastOrderListResponse)
        rvPastOrders!!.setLayoutManager(LinearLayoutManager(mContext))
        rvPastOrders!!.setAdapter(pastOrderAdapter)
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is PastOrderListResponse) {
                    val pastOrderListResponse: PastOrderListResponse = it.data
                    if (pastOrderListResponse!!.getCode() == Constants.success_code) {
                        showSuccessToast(pastOrderListResponse!!.getMessage()!!)

                        if (pastOrderListResponse.getBody()!!.size == 0) {
                            rvPastOrders!!.visibility = View.GONE
                            tvNoPastOrders!!.visibility = View.VISIBLE
                        } else {
                            rvPastOrders!!.visibility = View.VISIBLE
                            tvNoPastOrders!!.visibility = View.GONE
                            setPastOrdersAdapter(pastOrderListResponse!!.getBody())
                        }

                    } else {
                        CommonMethods.AlertErrorMessage(requireActivity(), pastOrderListResponse.getMessage())
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
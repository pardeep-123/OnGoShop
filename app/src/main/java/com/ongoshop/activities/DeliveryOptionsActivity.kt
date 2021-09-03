package com.ongoshop.activities


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.ongoshop.R
import com.ongoshop.adapter.DeliveryOptionsAdapter
import com.ongoshop.base.BaseActivity
import com.ongoshop.pojo.SignupResponsess
import com.ongoshop.pojo.VendorDeliveryCharge
import com.ongoshop.pojo.VendorDeliveryOption
import com.ongoshop.utils.helperclasses.DeliveryOptionsClicklisetener
import com.ongoshop.utils.others.SharedPrefUtil
import kotlinx.android.synthetic.main.activity_delivery_options_timings.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class DeliveryOptionsActivity : BaseActivity(), View.OnClickListener, DeliveryOptionsClicklisetener/*, Observer<RestObservable>*/ {

    lateinit var recyclerview: RecyclerView
    lateinit var deliveryOptions1Adapter: DeliveryOptionsAdapter
    private lateinit var mContext: DeliveryOptionsActivity
    private var vendorDeliveryOptionsList: ArrayList<VendorDeliveryOption>? = ArrayList()
    private var vendorDeliveryChargesList: ArrayList<VendorDeliveryCharge>? = ArrayList()
    private var vendorDeliveryOptionsupdatedList: ArrayList<VendorDeliveryOption>? = ArrayList()
    var makebuttonclickable = false

    private var vendorDeliveryOptionsListJsonArray =""

    override fun getContentId(): Int {
        return R.layout.activity_delivery_options_timings
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        btnConfirm.setOnClickListener(mContext)
        ivBack.setOnClickListener(mContext)

        recyclerview = findViewById(R.id.recyclerview)


        if (intent.extras !=null){
            vendorDeliveryOptionsList = intent.getParcelableArrayListExtra<VendorDeliveryOption>("vendorDeliveryOptions") as ArrayList<VendorDeliveryOption>
            Log.e("DeliOptionSize", vendorDeliveryOptionsList!!.size.toString())
            vendorDeliveryChargesList = intent.getParcelableArrayListExtra<VendorDeliveryCharge>("vendorDeliveryCharges") as ArrayList<VendorDeliveryCharge>
        val gson = GsonBuilder().create()

        var body = gson.fromJson<SignupResponsess.Body>(SharedPrefUtil.getInstance().getregisterr(),SignupResponsess.Body::class.java)

            deliveryOptions1Adapter = DeliveryOptionsAdapter(mContext, vendorDeliveryOptionsList!!, mContext, mContext)
            recyclerview.layoutManager = LinearLayoutManager(mContext)
            recyclerview.adapter = deliveryOptions1Adapter

        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnConfirm -> {


             val gson  =  GsonBuilder().create()
                val data =  gson.toJson(vendorDeliveryOptionsupdatedList)
               Log.e("data",data)

                if (vendorDeliveryOptionsupdatedList!!.isEmpty()) {
                    showSuccessToast(this,"Please select delivery or delivery date")

                }
                else
                {
                    for (i in 0 until vendorDeliveryOptionsupdatedList!!.size)
                    {
                        if (vendorDeliveryOptionsupdatedList!![i].noDelivery==1||vendorDeliveryOptionsupdatedList!![i].deliveryTimeTo.isNotEmpty())
                        {
                            makebuttonclickable = true
                        }
                        else
                        {
                            makebuttonclickable = false
                            showSuccessToast(this,"Please select no delivery or delivery date for ${vendorDeliveryOptionsupdatedList!![i].day}")
                            break
                        }
                    }
                }

                if (makebuttonclickable)
                    makejsonArray()
               }

            R.id.ivBack -> {
            onLeftIconClick()
            }
        }
    }

     fun setUpdatedList(pos: Int, vendorDeliveryOptionsList: ArrayList<VendorDeliveryOption>){
        vendorDeliveryOptionsupdatedList= vendorDeliveryOptionsList
        Log.e("delivertybe", vendorDeliveryOptionsupdatedList!!.get(pos).deliveryTimeTo)
        Log.e("list", vendorDeliveryOptionsupdatedList.toString())
    }

    fun makejsonArray() {
        val jsonArray = JSONArray()
        for (i in 0 until vendorDeliveryOptionsupdatedList!!.size) {
            val vendorDeliveryOptionLists = JSONObject()
            try {
                vendorDeliveryOptionLists.put("id", vendorDeliveryOptionsupdatedList!!.get(i).id)
                vendorDeliveryOptionLists.put("vendorId", vendorDeliveryOptionsupdatedList!!.get(i).vendorId)
                vendorDeliveryOptionLists.put("sortOrder", vendorDeliveryOptionsupdatedList!!.get(i).sortOrder)
                vendorDeliveryOptionLists.put("day", vendorDeliveryOptionsupdatedList!!.get(i).day)
                vendorDeliveryOptionLists.put("deliveryTimeFrom", vendorDeliveryOptionsupdatedList!!.get(i).deliveryTimeFrom)
                vendorDeliveryOptionLists.put("deliveryTimeTo", vendorDeliveryOptionsupdatedList!!.get(i).deliveryTimeTo)
                vendorDeliveryOptionLists.put("noDelivery", vendorDeliveryOptionsupdatedList!!.get(i).noDelivery)
                vendorDeliveryOptionLists.put("created", vendorDeliveryOptionsupdatedList!!.get(i).created)
                vendorDeliveryOptionLists.put("updated", vendorDeliveryOptionsupdatedList!!.get(i).updated)
                vendorDeliveryOptionLists.put("createdAt", vendorDeliveryOptionsupdatedList!!.get(i).createdAt)
                vendorDeliveryOptionLists.put("updatedAt", vendorDeliveryOptionsupdatedList!!.get(i).updatedAt)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            jsonArray.put(vendorDeliveryOptionLists)

        }
        Log.e("jsonString:", jsonArray.toString())
        /* }*/


        if (intent != null) {
            val intent = Intent(mContext, MaximumDeliveryPerDayActivity::class.java)
            intent.putExtra("shopName", getIntent().getStringExtra("shopName"))
            intent.putExtra("categoryName", getIntent().getStringExtra("categoryName"))
            intent.putExtra("shop_category_id", getIntent().getStringExtra("shop_category_id"))
            intent.putExtra("shopABN", getIntent().getStringExtra("shopABN"))
            intent.putExtra("buildingNumber", getIntent().getStringExtra("buildingNumber"))
            intent.putExtra("streetNumber", getIntent().getStringExtra("streetNumber"))
            intent.putExtra("city", getIntent().getStringExtra("city"))
            intent.putExtra("state", getIntent().getStringExtra("state"))
            intent.putExtra("country", getIntent().getStringExtra("country"))
            intent.putExtra("postalCode", getIntent().getStringExtra("postalCode"))
            intent.putExtra("openTime", getIntent().getStringExtra("openTime"))
            intent.putExtra("closeTime", getIntent().getStringExtra("closeTime"))
            intent.putExtra("shopImage", getIntent().getStringExtra("shopImage"))
            intent.putExtra("homeDelivery", getIntent().getStringExtra("homeDelivery"))

            intent.putExtra("deliveryOptionsJsonString", jsonArray.toString() )
            intent.putParcelableArrayListExtra("vendorDeliveryCharges", vendorDeliveryChargesList)
            startActivity(intent)
        }


    }
    override fun deliveryOptionClick(deliveryOptionJsonArray: String) {
        vendorDeliveryOptionsListJsonArray=deliveryOptionJsonArray
    }

}

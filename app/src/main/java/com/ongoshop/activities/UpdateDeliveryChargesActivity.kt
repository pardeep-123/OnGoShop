package com.ongoshop.activities


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.ongoshop.R
import com.ongoshop.adapter.DeliveryChargesAdapter
import com.ongoshop.adapter.UpdateDeliveryChargesAdapter
import com.ongoshop.adapter.UpdateDeliveryOptionsAdapter
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.EditProfileAddShopResponsess
import com.ongoshop.pojo.VendorDeliveryCharge
import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.utils.others.SharedPrefUtil
import com.ongoshop.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.activity_delivery_charges.*

import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.util.*


class UpdateDeliveryChargesActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    private val viewModel: AuthViewModel
            by lazy { ViewModelProviders.of(this).get(AuthViewModel::class.java) }
    
    var dialog: Dialog? = null
    lateinit var deliveryChargesRecyclerview: RecyclerView
    lateinit var deliveryChargesAdapter: UpdateDeliveryChargesAdapter

    private lateinit var mContext: UpdateDeliveryChargesActivity

    private var vendorDeliveryChargesList: ArrayList<VendorDeliveryCharge>? = ArrayList()
    private var vendorDeliveryChargesupdatedList: ArrayList<VendorDeliveryCharge>? = ArrayList()

    var makebuttonclickable = false
    override fun getContentId(): Int {
        return R.layout.activity_delivery_charges
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        deliveryChargesRecyclerview = findViewById(R.id.recyclerview)



        btnConfirm.setOnClickListener(mContext)
        ivBack.setOnClickListener(mContext)

        if (intent != null) {
            vendorDeliveryChargesList = intent.getParcelableArrayListExtra<VendorDeliveryCharge>("vendorDeliveryCharges")
                    as ArrayList<VendorDeliveryCharge>

            deliveryChargesAdapter = UpdateDeliveryChargesAdapter(mContext, vendorDeliveryChargesList!!, mContext)
            deliveryChargesRecyclerview.layoutManager = LinearLayoutManager(mContext)
            deliveryChargesRecyclerview.adapter = deliveryChargesAdapter

        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnConfirm -> {

                if (vendorDeliveryChargesupdatedList!!.isEmpty())
                {
                    showSuccessToast(this,"Please select delivery charges")

                }
                else
                {
                    for (i in 0 until vendorDeliveryChargesupdatedList!!.size)
                    {
                        if (vendorDeliveryChargesupdatedList!![i].freeDelivery==1 || vendorDeliveryChargesupdatedList!![i].price>0 )
                        {
                            makebuttonclickable = true
                        }


                        else
                        {
                            showSuccessToast(this,"Please select free delivery or charges for ${vendorDeliveryChargesupdatedList!![i].minDistance}-${vendorDeliveryChargesupdatedList!![i].maxDistance}")
                            makebuttonclickable  = false
                            break
                        }
                    }
                    if (makebuttonclickable)
                        makejsonArray()

                }

            }

            R.id.ivBack -> {
                onLeftIconClick()
            }
        }
    }

    private fun isValid(): Boolean {
        var check = false
        if (!mValidationClass.isNetworkConnected)
            showAlerterRed(resources.getString(R.string.no_internet))
        else
            check = true
        return check
    }

    private fun addShopApi(deliveryChargesJsonArray: String) {
        if (isValid()) {
            if (intent != null) {
              //  val bodyimage = mValidationClass.prepareFilePart("shopLogo", File(intent.getStringExtra("shopImage")))
                val partShopName = mValidationClass.createPartFromString(intent.getStringExtra("shopName"))
                val partCategoryName = mValidationClass.createPartFromString(intent.getStringExtra("shopCategory"))
                val partShopABN = mValidationClass.createPartFromString(intent.getStringExtra("shopABN"))
                val partBuildingNumber = mValidationClass.createPartFromString(intent.getStringExtra("shopBuildingNumber"))
                val partStreetNumber = mValidationClass.createPartFromString(intent.getStringExtra("shopStreetNumber"))
                val partCity = mValidationClass.createPartFromString(intent.getStringExtra("shopCity"))
                val partState = mValidationClass.createPartFromString(intent.getStringExtra("shopState"))
                val partCountry = mValidationClass.createPartFromString(intent.getStringExtra("shopCountry"))
                val partPostalCode = mValidationClass.createPartFromString(intent.getStringExtra("shopPostCode"))
                val partOpenTime = mValidationClass.createPartFromString(intent.getStringExtra("openTime"))
                val partCloseTime = mValidationClass.createPartFromString(intent.getStringExtra("closeTime"))
                val partHomeDelivery = mValidationClass.createPartFromString(intent.getStringExtra("homeDelivery"))
                val vendorId = mValidationClass.createPartFromString(SharedPrefUtil.getInstance().userId)
                val partDeliveriesPerDay = mValidationClass.createPartFromString(intent.getStringExtra("deliveriesPerDay"))
                val partDeliveryChargesJsonArrayString = mValidationClass.createPartFromString(deliveryChargesJsonArray)

                val partToken = mValidationClass.createPartFromString(SharedPrefUtil.getInstance().deviceToken)
                val partType = mValidationClass.createPartFromString(Constants.Device_Type)

                val map = HashMap<String, RequestBody>()
                map.put("shopName", partShopName)
                map.put("shopCategory", partCategoryName)
                map.put("abn", partShopABN)
                map.put("buildingNumber", partBuildingNumber)
                map.put("streetNumber", partStreetNumber)
                map.put("city", partCity)
                map.put("state", partState)
                map.put("country", partCountry)
                map.put("postalCode", partPostalCode)
                map.put("shopOpenTime", partOpenTime)
                map.put("shopCloseTime", partCloseTime)
                map.put("homeDelivery", partHomeDelivery)
                map.put("deviceType", partType)
                map.put("deviceToken", partToken)
                map.put("vendorId", vendorId)
                map.put("deliveriesPerDay", partDeliveriesPerDay)
                map.put("homeDelivery", partHomeDelivery)
               map.put("vendorDeliveryCharges", partDeliveryChargesJsonArrayString)

                var bodyimage = ""

                if (!intent.getStringExtra("shopImage").equals("NoImageChanged")) {
                    bodyimage = intent.getStringExtra("shopImage")!!
                } else {
                    bodyimage = ""
                }

                viewModel.editShopDelivery(this, true, map, bodyimage, mValidationClass)
                viewModel.mResponse.observe(this, this)

            }
        }
    }


    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is EditProfileAddShopResponsess) {
                    val addShopResponsess: EditProfileAddShopResponsess = it.data
                    if (addShopResponsess.code == Constants.success_code) {
                        showSuccessToast(mContext, addShopResponsess.message)
//
//                        MyApplication.getnstance()
//                                .setString(
//                                        Constants.AuthKey,
//                                        addShopResponsess!!.body.token!!
//                                )
                        MyApplication.instance!!.setString(Constants.UserData, modelToString(addShopResponsess))

                      //  SharedPrefUtil.getInstance().saveAuthToken(addShopResponsess.body.token)
                        if (addShopResponsess.body.image!=null)
                        SharedPrefUtil.getInstance().saveImage(addShopResponsess.body.image)
                        SharedPrefUtil.getInstance().saveUserId(addShopResponsess.body.id.toString())
                        if (addShopResponsess.body.email!=null)
                        SharedPrefUtil.getInstance().saveEmail(addShopResponsess.body.email)
                        if (addShopResponsess.body.name!=null)
                        SharedPrefUtil.getInstance().saveName(addShopResponsess.body.name)

                        val gson= GsonBuilder().create()

                        SharedPrefUtil.getInstance().editProfilerespo(gson.toJson(addShopResponsess.body))

                        val intent = Intent(mContext, HomeActivity::class.java)
                        Constants.currentFragment= "Profile"
                        startActivity(intent)
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

    fun setUpdatedList(pos: Int, vendorDeliveryChargeList: ArrayList<VendorDeliveryCharge>) {
        vendorDeliveryChargesupdatedList = vendorDeliveryChargeList
        Log.e("priceee", vendorDeliveryChargesupdatedList!!.get(pos).price.toString())
    }

    
    fun makejsonArray() {
        val jsonArray = JSONArray()
        for (i in 0 until vendorDeliveryChargesupdatedList!!.size) {
            val vendorDeliveryChargesLists = JSONObject()
            try {
                vendorDeliveryChargesLists.put("id", vendorDeliveryChargesupdatedList!!.get(i).id)
                vendorDeliveryChargesLists.put("vendorId", vendorDeliveryChargesupdatedList!!.get(i).vendorId)
                vendorDeliveryChargesLists.put("minDistance", vendorDeliveryChargesupdatedList!!.get(i).minDistance)
                vendorDeliveryChargesLists.put("maxDistance", vendorDeliveryChargesupdatedList!!.get(i).maxDistance)
                vendorDeliveryChargesLists.put("price", vendorDeliveryChargesupdatedList!!.get(i).price)
                vendorDeliveryChargesLists.put("noDelivery", vendorDeliveryChargesupdatedList!!.get(i).noDelivery)
                vendorDeliveryChargesLists.put("freeDelivery", vendorDeliveryChargesupdatedList!!.get(i).freeDelivery)
                vendorDeliveryChargesLists.put("created", vendorDeliveryChargesupdatedList!!.get(i).created)
                vendorDeliveryChargesLists.put("updated", vendorDeliveryChargesupdatedList!!.get(i).updated)
                vendorDeliveryChargesLists.put("createdAt", vendorDeliveryChargesupdatedList!!.get(i).createdAt)
                vendorDeliveryChargesLists.put("updatedAt", vendorDeliveryChargesupdatedList!!.get(i).updatedAt)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            jsonArray.put(vendorDeliveryChargesLists)


        }
        Log.e("jsonString:", jsonArray.toString())
        /* }*/

        addShopApi(jsonArray.toString())



    }

}
